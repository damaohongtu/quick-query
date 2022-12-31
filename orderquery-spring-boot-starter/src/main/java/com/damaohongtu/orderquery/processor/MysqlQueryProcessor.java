package com.damaohongtu.orderquery.processor;

import com.damaohongtu.orderquery.dto.data.Condition;
import com.damaohongtu.orderquery.dto.data.Element;
import com.damaohongtu.orderquery.dto.graph.Node;
import com.damaohongtu.orderquery.dto.source.MysqlSource;
import com.damaohongtu.orderquery.enums.DataSourceTypeEnum;
import com.damaohongtu.orderquery.executor.DataBaseExecutor;
import com.damaohongtu.orderquery.util.ShardingUtil;
import com.google.gson.Gson;
import com.damaohongtu.orderquery.dal.entity.OrderQueryHash;
import com.damaohongtu.orderquery.dal.repo.OrderQueryHashRepo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: 大袤宏图
 * FileName: MysqlOrderQueryProcessor
 * Description: MySQL数据源查询处理器
 */

@Component
public class MysqlQueryProcessor extends QueryProcessor {

    private static final String SQL_EQUAL_TEMPLATE = "SELECT %s FROM %s WHERE %s='%s' LIMIT 100";

    private static final String SQL_IN_TEMPLATE = "SELECT {0} FROM {1} WHERE {2} IN ({3}) LIMIT 100";
    private static final String SQL_LIKE_TEMPLATE = "SELECT {0} FROM {1} WHERE {2} LIKE {3} LIMIT 100";

    @Resource
    private DataBaseExecutor dataBaseExecutor;

    @Resource
    private OrderQueryHashRepo OrderQueryHashRepo;

    @Override
    public List<Map> fetch(Node node, Condition condition){

        List<Map>  result = new ArrayList<>();
        List<Object> values = condition.getValues();

        // 解析数据源
        Gson gson = new Gson();
        MysqlSource config = gson.fromJson(node.getDataSource(), MysqlSource.class);

        // 获取分库分表信息
        String dataSource = config.getDataSource();

        OrderQueryHash OrderQueryHash = OrderQueryHashRepo.select(config.getHash());

        String script = OrderQueryHash.getScript();

        for(Object serialNo : values){

            int dbIndex = ShardingUtil.parseDbIndex(script, String.valueOf(serialNo));
            String tablePostfix = ShardingUtil.parseTablePostfix(script, String.valueOf(serialNo));

            // 分库分表
            String table = config.getTable() + tablePostfix;
            String resultField = String.join(",",
                    node.getOutputField().stream().map(Element::getKey).collect(Collectors.toList()));

            // 组装SQL
            String sql = this.genMysqlSql(resultField, table, condition.getKey(), String.valueOf(serialNo));

            // 查询
            try {
                result.addAll(dataBaseExecutor.execute(DataSourceTypeEnum.EXTERNAL_MYSQL.getCode(), dataSource, dbIndex, sql));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return result;
    }

    /**
     * 组装查询sql，并检查sql注入问题
     * @param resultField
     * @param table
     * @param conditionField
     * @param conditionValue
     * @return
     */
    private String genMysqlSql(String resultField, String table, String conditionField, String conditionValue){
        String sql = String.format(SQL_EQUAL_TEMPLATE, resultField, table, conditionField, conditionValue);
        return sql;
    }
}
