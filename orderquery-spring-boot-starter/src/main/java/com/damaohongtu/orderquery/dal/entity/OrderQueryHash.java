package com.damaohongtu.orderquery.dal.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author: 大袤宏图
 * FileName: OrderQueryHash
 * Description: 分库分表策略
 */
@Data
public class OrderQueryHash {

    /**
     * ID
     */
    private Long id;

    /**
     * 分库分表配置编码
     */
    private String code;

    /**
     * 分库分表配置名字
     */
    private String name;

    /**
     * 分库分表脚本
     */
    private String script;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除键
     */
    private Byte valid;
}
