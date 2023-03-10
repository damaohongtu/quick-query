package com.damaohongtu.quickquery.dto.graph;

import lombok.Data;

/**
 * @author: 大袤宏图
 * FileName: NodeRelation
 * Description: 节点之间的关系
 */
@Data
public class RelationDto {

    private String fromNode;

    private String fromField;

    private String toNode;

    private String toField;

}
