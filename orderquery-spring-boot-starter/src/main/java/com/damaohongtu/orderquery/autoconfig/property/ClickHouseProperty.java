package com.damaohongtu.orderquery.autoconfig.property;

import lombok.Data;

/**
 * @author: 大袤宏图
 * FileName: MysqlConfig
 * Description: ClickHouse数据库配置
 */

@Data
public class ClickHouseProperty {

    private String username;

    private String password;

    private String url;

}
