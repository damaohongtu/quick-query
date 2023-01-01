package com.damaohongtu.quickquery.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: 大袤宏图
 * FileName: MessageProcessorEnum
 * Description: 查询处理器枚举
 */

@Getter
@AllArgsConstructor
public enum QuickQueryProcessorEnum {

    MYSQL_PROCESSOR("mysql", "MYSQL处理器", "com.damaohongtu.orderquery.processor.MysqlQueryProcessor"),
    HIVE_PROCESSOR("hive", "MYSQL处理器", "com.damaohongtu.orderquery.processor.HiveQueryProcessor"),
    CK_PROCESSOR("clickhouse", "MYSQL处理器", "com.damaohongtu.orderquery.processor.ClickHouseQueryProcessor"),
    HTTP_PROCESSOR("http", "MYSQL处理器", "com.damaohongtu.orderquery.processor.HttpQueryProcessor");


    /**
     * 查询处理起编码：配置前端、数据库、代码三个地方保持一致
     */
    private String code;

    /**
     * 处理器名称
     */
    private String name;

    /**
     * 全类名
     */
    private String queryProcessor;

    public static QuickQueryProcessorEnum codeOf(String code){
        for(QuickQueryProcessorEnum processorEnum:values()){
            if(processorEnum.getCode().equals(code)){
                return processorEnum;
            }
        }
        return null;
    }

}
