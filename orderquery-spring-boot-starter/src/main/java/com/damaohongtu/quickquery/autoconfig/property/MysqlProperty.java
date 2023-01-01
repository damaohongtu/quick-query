package com.damaohongtu.quickquery.autoconfig.property;

import lombok.Data;

/**
 * @author: 大袤宏图
 * FileName: MysqlConfig
 * Description: MySQL数据库配置
 */

@Data
public class MysqlProperty {

    private String username;

    private String password;

    private String url;

    private String driverClassName;

}
