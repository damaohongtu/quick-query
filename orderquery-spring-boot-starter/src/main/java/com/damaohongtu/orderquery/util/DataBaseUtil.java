package com.damaohongtu.orderquery.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author: 大袤宏图
 * FileName: DataBaseUtil
 * Description: 数据库工具类
 */

public class DataBaseUtil {

    public static List<Map> convertResultSet(ResultSet resultSet) throws SQLException {
        List<Map> list = new ArrayList<>();
        if(Objects.isNull(resultSet)){
            return list;
        }
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        while (resultSet.next()) {
            Map<String, Object> rawMap = new HashMap<>(8);
            for (int i = 1; i <= columnCount; i++) {
                String columnTypeName = metaData.getColumnTypeName(i);
                String columnName = metaData.getColumnName(i);
                rawMap.put(columnName, resultSet.getString(columnName));
            }
            list.add(rawMap);
        }
        return list;
    }
}
