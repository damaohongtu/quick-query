package com.damaohongtu.orderquery.dto.graph;

import lombok.Data;

/**
 * @author: 大袤宏图
 * FileName: NodeRelation
 * Description: 节点之间的关系
 */
@Data
public class Relation {

    private String fromNode;

    private String fromField;

    private String toNode;

    private String toField;

}
