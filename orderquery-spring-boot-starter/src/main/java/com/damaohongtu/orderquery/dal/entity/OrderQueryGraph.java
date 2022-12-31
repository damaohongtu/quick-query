package com.damaohongtu.orderquery.dal.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author: 大袤宏图
 * FileName: OrderQueryGraph
 * Description: 关联图
 */

@Data
public class OrderQueryGraph {

    private Long id;

    /**
     * 关联图编码
     */
    private String graphCode;

    /**
     * 关联图名
     */
    private String graphName;

    /**
     * 关联图边信息(用于图展示)
     */
    private String edges;

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
