package com.damaohongtu.orderquery.dto.source;

import lombok.Data;

/**
 * @author: 大袤宏图
 * FileName: HashStrategy
 * Description: 分库分表Hash规则
 */
@Data
public class HashStrategy {

    /**
     * 是否分表
     */
    private Boolean split;

    /**
     * 是否分库
     */
    private Boolean dbSplit;

    /**
     * 数据库数量
     */
    private Integer dbCount;

    /**
     * 表格数量
     */
    private Integer tableCount;

    /**
     * 时间：year, month, week
     */
    private String time;

    /**
     * 使用shardingJdbc inline语法定义的分库分表规则
     */
    private String shardingJdbcRule;

}
