package com.damaohongtu.quickquery.executor;

import com.alibaba.druid.pool.DruidDataSource;
import com.damaohongtu.quickquery.enums.DataSourceTypeEnum;
import com.damaohongtu.quickquery.util.DataBaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.clickhouse.ClickHouseDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 大袤宏图
 * Description: 动态数据源，动态SQL执行，统一使用JDBC处理
 */
@Service
@Slf4j
public class DataBaseExecutor {
    
    @Resource
    @Qualifier("internal.mysql")
    private DruidDataSource internalDruidDataSource;

    @Resource
    @Qualifier("external.mysql")
    private Map<String, List<DruidDataSource>> externalDruidDataSourceMap;

    @Resource
    @Qualifier("external.clickhouse")
    private Map<String, ClickHouseDataSource> externalClickHouseDataSourceMap;

    @Resource
    @Qualifier("external.hive")
    private Map<String, DruidDataSource> externalHiveDataSourceMap;


    /**
     * 使用druid接口执行sql
     * @param dataSourceType
     * @param dataSourceName
     * @param sql
     * @return
     * @throws SQLException
     */
    public List<Map> execute(String dataSourceType, String dataSourceName, Integer dataSourceIndex, String sql) throws SQLException {
        // 获取连接池
        DataSource dataSource = getDataSource(dataSourceType, dataSourceName, dataSourceIndex);
        if (dataSource == null) {
            return null;
        }
        Connection connection = null;
        List<Map> result = null;
        try {
            connection = dataSource.getConnection();
            // 执行SQL
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute(sql);
            result = DataBaseUtil.convertResultSet(statement.getResultSet());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connection.close();
        }
        // 获取结果
        return result;
    }

    /**
     * 获取当前表格所有的字段的信息
     * @param dataSourceType
     * @param dataSourceName
     * @param table
     * @return
     */
    public List getTableInfo(String dataSourceType, String dataSourceName, String table){
        List result = new ArrayList();
        DatabaseMetaData dbmd = null;
        Connection conn = null;
        try {
            DataSource dataSource = this.getDataSource(dataSourceType, dataSourceName, 0);
            conn = dataSource.getConnection();
            dbmd = conn.getMetaData();
            ResultSet resultSet = dbmd.getTables(null, this.getSchema(conn), table, new String[] { "TABLE" });
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                log.info(String.format("打印tableName: %s", tableName));
                if(tableName.equals(table)){
                    ResultSet rs = conn.getMetaData().getColumns(null, this.getSchema(conn), tableName, "%");
                    while(rs.next()){
                        Map map = new HashMap();
                        String colName = rs.getString("COLUMN_NAME");
                        map.put("code", colName);

                        String remarks = rs.getString("REMARKS");
                        if(remarks == null || remarks.equals("")){
                            remarks = colName;
                        }
                        map.put("name",remarks);

                        String dbType = rs.getString("TYPE_NAME");
                        map.put("dbType",dbType);

                        map.put("valueType", changeDbType(dbType));
                        result.add(map);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private String changeDbType(String dbType) {
        dbType = dbType.toUpperCase();
        switch(dbType){
            case "VARCHAR":
            case "VARCHAR2":
            case "CHAR":
                return "1";
            case "NUMBER":
            case "DECIMAL":
                return "4";
            case "INT":
            case "SMALLINT":
            case "INTEGER":
                return "2";
            case "BIGINT":
                return "6";
            case "DATETIME":
            case "TIMESTAMP":
            case "DATE":
                return "7";
            default:
                return "1";
        }
    }

    /**
     * 获取schema：其他数据库不需要这个方法 oracle和db2需要
     * @param conn
     * @return
     * @throws Exception
     */
    private String getSchema(Connection conn) throws Exception {
        String schema;
        schema = conn.getMetaData().getUserName();
        if ((schema == null) || (schema.length() == 0)) {
            throw new Exception("ORACLE数据库模式不允许为空");
        }
        return schema.toString();
    }

    /**
     * 获取数据源
     * @param dataSourceType
     * @param dataSourceName
     * @param dataSourceIndex
     * @return
     */
    private DataSource getDataSource(String dataSourceType, String dataSourceName, Integer dataSourceIndex) {
        DataSource dataSource = null;
        DataSourceTypeEnum dataSourceTypeEnum = DataSourceTypeEnum.codeOf(dataSourceType);
        switch (dataSourceTypeEnum){
            case INTERNAL_MYSQL:
                dataSource = internalDruidDataSource;
                break;
            case EXTERNAL_MYSQL:
                dataSource = externalDruidDataSourceMap.get(dataSourceName).get(dataSourceIndex);
                break;
            case EXTERNAL_CLICKHOUSE:
                dataSource = externalClickHouseDataSourceMap.get(dataSourceName);
                break;
            case EXTERNAL_HIVE:
                dataSource = externalHiveDataSourceMap.get(dataSourceName);
                break;
            default:
                return null;
        }
        return dataSource;
    }

    public List<String> listDataBaseName(DataSourceTypeEnum dataSourceTypeEnum ){
        List<String> dataBaseNameList = new ArrayList<>();
        switch(dataSourceTypeEnum){
            case EXTERNAL_MYSQL:
                for(String key:externalDruidDataSourceMap.keySet()){
                    dataBaseNameList.add(key);
                }
                break;
            case EXTERNAL_CLICKHOUSE:
                for(String key:externalClickHouseDataSourceMap.keySet()){
                    dataBaseNameList.add(key);
                }
                break;
            case EXTERNAL_HIVE:
                for(String key:externalHiveDataSourceMap.keySet()){
                    dataBaseNameList.add(key);
                }
                break;
        }
        return dataBaseNameList;
    }

    public Map<String, List<String>> listDatabase(){
        Map<String, List<String>> res = new HashMap<>(8);
        List<String> mysql = new ArrayList<>();
        for(String key:externalDruidDataSourceMap.keySet()){
            mysql.add(key);
        }
        List<String> hive = new ArrayList<>();
        for(String key:externalClickHouseDataSourceMap.keySet()){
            hive.add(key);
        }
        List<String> clickhouse = new ArrayList<>();
        for(String key:externalHiveDataSourceMap.keySet()){
            clickhouse.add(key);
        }

        res.put("mysql", mysql);
        res.put("hive", hive);
        res.put("clickhouse", clickhouse);

        return res;
    }


}
