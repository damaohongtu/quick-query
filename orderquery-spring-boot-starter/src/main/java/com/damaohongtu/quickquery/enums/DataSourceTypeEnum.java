package com.damaohongtu.quickquery.enums;

import lombok.Getter;

/**
 * @author: 大袤宏图
 * FileName: DataSourceTypeEnum
 * Description: 数据源类型枚举
 */
@Getter
public enum DataSourceTypeEnum {

    INTERNAL_MYSQL("internal.mysql", "系统内部mysql"),

    EXTERNAL_MYSQL("external.mysql", "系统外部mysql"),

    EXTERNAL_CLICKHOUSE("external.clickhouse", "系统外部clickhouse"),

    EXTERNAL_HIVE("external.hive", "系统外部hive"),

    ;

    private String code;
    private String name;

    DataSourceTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static DataSourceTypeEnum codeOf(String code){
        for (DataSourceTypeEnum value:values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }

}
