package com.damaohongtu.orderquery.autoconfig.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * @author: 大袤宏图
 * FileName: OrderQueryDataSourceConfig
 * Description: 外部数据源配置
 */

@Data
@ConfigurationProperties(prefix = "orderquery.external.datasource")
public class OrderQueryDataSourceConfig {

    /**
     * 外部mysql数据源
     */
    private Map<String, List<MysqlProperty>> mysql;

    /**
     * 外部clickHouse数据源
     */
    private Map<String, ClickHouseProperty> clickHouse;

    /**
     * 外部hive数据源
     */
    private Map<String, HiveProperty> hive;

}
