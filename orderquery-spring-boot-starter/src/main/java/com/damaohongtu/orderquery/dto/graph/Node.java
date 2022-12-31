package com.damaohongtu.orderquery.dto.graph;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.damaohongtu.orderquery.dal.entity.OrderQueryNode;
import com.damaohongtu.orderquery.dto.data.Element;
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
public class Node {

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
    private List<Relation> neighborNode;

    public static Node fromDo(OrderQueryNode node){
        Gson gson = new Gson();
        Node nodeDto = Node.builder()
                .nodeCode(node.getNodeCode())
                .graphCode(node.getGraphCode())
                .nodeName(node.getNodeName())
                .nodeType(node.getNodeType())
                .dataSource(node.getDataSource())
                .inputField(gson.fromJson(node.getInputField(), Element.class))
                .outputField(gson.fromJson(node.getOutputField(), new TypeToken<List<Element>>(){}.getType()))
                .routeRule(node.getRouteRule())
                .neighborNode(gson.fromJson(node.getNeighborNode(), new TypeToken<List<Relation>>(){}.getType()))
                .build();
        return nodeDto;
    }


}
