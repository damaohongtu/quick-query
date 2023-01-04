package com.damaohongtu.quickquery.dto.graph;

import com.damaohongtu.quickquery.dao.entity.Node;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.damaohongtu.quickquery.dto.data.Element;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author: 大袤宏图
 * FileName: OrderQueryGraph
 * Description: 关联图节点
 */

@Data
@Builder
@AllArgsConstructor
public class NodeDto {

    /**
     * 节点编码
     */
    private String nodeCode;

    /**
     * 关联图编码
     */
    private Long graphId;

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
     * 输入字段：当前节点作为入口的时候的入口字段
     */
    private Element inputField;

    /**
     * 输出字段
     */
    private List<Element> outputField;

    /**
     * 路由规则
     */
    private String routeRule;

    /**
     * 邻接节点
     */
    private List<RelationDto> neighborNode;

    public static NodeDto fromPo(Node node){
        Gson gson = new Gson();
        NodeDto nodeDto = NodeDto.builder()
                .nodeCode(node.getNodeCode())
                .graphId(node.getGraphId())
                .nodeName(node.getNodeName())
                .nodeType(node.getNodeType())
                .dataSource(node.getDataSource())
                .inputField(gson.fromJson(node.getInputField(), Element.class))
                .outputField(gson.fromJson(node.getOutputField(), new TypeToken<List<Element>>(){}.getType()))
                .routeRule(node.getRouteRule())
                .neighborNode(gson.fromJson(node.getNeighborNode(), new TypeToken<List<RelationDto>>(){}.getType()))
                .build();
        return nodeDto;
    }


}
