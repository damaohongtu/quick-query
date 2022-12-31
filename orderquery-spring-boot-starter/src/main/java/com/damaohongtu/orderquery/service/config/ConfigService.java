package com.damaohongtu.orderquery.service.config;

import com.damaohongtu.orderquery.dto.config.OrderQueryConfigDto;
import com.damaohongtu.orderquery.dal.entity.OrderQueryGraph;
import com.damaohongtu.orderquery.dal.entity.OrderQueryNode;
import com.damaohongtu.orderquery.dal.repo.OrderQueryGraphRepo;
import com.damaohongtu.orderquery.dal.repo.OrderQueryNodeRepo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 大袤宏图
 * FileName: ConfigService
 * Description: 配置服务
 */

@Service
public class ConfigService {

    @Resource
    private OrderQueryGraphRepo OrderQueryGraphRepo;

    @Resource
    private OrderQueryNodeRepo OrderQueryNodeRepo;

    public String config(OrderQueryConfigDto OrderQueryConfigDto){

        OrderQueryGraph OrderQueryGraph = new OrderQueryGraph();
        OrderQueryGraph.setGraphCode(OrderQueryConfigDto.getBizCode());
        OrderQueryGraph.setGraphName(OrderQueryConfigDto.getBizName());
        OrderQueryGraph.setEdges(OrderQueryConfigDto.getEdges());
        OrderQueryGraphRepo.insert(OrderQueryGraph);

        for(OrderQueryConfigDto.Node node : OrderQueryConfigDto.getNodes()){
            OrderQueryNode OrderQueryNode = new OrderQueryNode();
            OrderQueryNode.setNodeCode(node.getId());
            OrderQueryNode.setGraphCode(OrderQueryConfigDto.getBizCode());
            OrderQueryNode.setNodeName(node.getNodeName());
            OrderQueryNode.setNodeType(node.getNodeType());
            OrderQueryNode.setDataSource(node.getConfigInfo().getDataSource());
            OrderQueryNode.setInputField(node.getConfigInfo().getInputField());
            OrderQueryNode.setOutputField(node.getConfigInfo().getOutputField());
            OrderQueryNode.setRouteRule(node.getConfigInfo().getRouteRule());
            OrderQueryNode.setNeighborNode(node.getConfigInfo().getRelations());
            OrderQueryNode.setCoordinate(node.getCoordinate());
            OrderQueryNodeRepo.insert(OrderQueryNode);
        }

        return "SUCCESS";
    }

    public OrderQueryConfigDto query(String graphCode){
        OrderQueryGraph OrderQueryGraph = OrderQueryGraphRepo.selectByGraph(graphCode);
        List<OrderQueryNode> OrderQueryNodeList = OrderQueryNodeRepo.selectByGraph(graphCode);

        List<OrderQueryConfigDto.Node> nodes = new ArrayList<>();
        OrderQueryNodeList.forEach(item -> {
            OrderQueryConfigDto.ConfigInfo configInfo = OrderQueryConfigDto.ConfigInfo.builder()
                    .dataSource(item.getDataSource())
                    .inputField(item.getInputField())
                    .outputField(item.getOutputField())
                    .routeRule(item.getRouteRule())
                    .relations(item.getNeighborNode())
                    .build();
            OrderQueryConfigDto.Node node = OrderQueryConfigDto.Node.builder()
                    .id(item.getNodeCode())
                    .nodeType(item.getNodeType())
                    .coordinate(item.getCoordinate())
                    .configInfo(configInfo)
                    .build();
            nodes.add(node);
        });

        OrderQueryConfigDto orderQueryConfigDto = OrderQueryConfigDto.builder()
                .bizCode(OrderQueryGraph.getGraphCode())
                .bizName(OrderQueryGraph.getGraphName())
                .bizDesc("")
                .nodes(nodes)
                .edges(OrderQueryGraph.getEdges())
                .build();

        return orderQueryConfigDto;
    }

}
