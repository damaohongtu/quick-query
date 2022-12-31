package com.damaohongtu.orderquery.dto.source;

import lombok.Data;
/**
 * @author: 大袤宏图
 * FileName: MysqlConfig
 * Description: mysql节点配置
 * (1) 分库分表规则使用行表达式配置，使用当当网的sharding-jdbc解析：eg. db$->{0..1}.t_order$->{0..1}
 *      基于StandardShardingStrategy只支持单分片键，提供PreciseShardingAlgorithm（精准分片）和RangeShardingAlgorithm（范围分片）
 */

@Data
public class MysqlSource {

    /**
     * 分库分表后单个节点可以存在多个数据源
     */
    private String dataSource;

    /**
     * 表名
     */
    private String table;

    /**
     * 分库分表规则
     */
    private String hash;

}
