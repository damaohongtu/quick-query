package com.damaohongtu.orderquery.dal.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author: 大袤宏图
 * FileName: OrderQueryGraph
 * Description: 关联图节点
 */

@Data
public class OrderQueryNode {

    private Long id;

    /**
     * 节点编码
     */
    private String nodeCode;

    /**
     * 关联图编码
     */
    private String graphCode;

    /**
     * 节点名
     */
    private String nodeName;

    /**
     * 节点类型
     */
    private String nodeType;

    /**
     * 数据源
     */
    private String dataSource;

    /**
     * 输入字段
     */
    private String inputField;

    /**
     * 输出字段
     */
    private String outputField;

    /**
     * 路由规则: 正则表达式
     */
    private String routeRule;

    /**
     * 邻接节点
     */
    private String neighborNode;

    /**
     * 节点坐标(用于图展示)
     */
    private String coordinate;

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
