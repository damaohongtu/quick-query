package com.damaohongtu.orderquery.autoconfig.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: 大袤宏图
 * FileName: OrderQueryConfig
 * Description: OrderQuery初始化属性
 */
@Data
@ConfigurationProperties(prefix = "orderquery.internal.datasource")
public class OrderQueryConfig {

    /**
     * 关联图存储数据源
     */
    private MysqlProperty mysql;

}
