package com.damaohongtu.orderquery.service.config;

import com.damaohongtu.orderquery.dao.entity.Graph;
import com.damaohongtu.orderquery.dao.entity.Node;
import com.damaohongtu.orderquery.dao.repo.GraphRepo;
import com.damaohongtu.orderquery.dao.repo.NodeRepo;
import com.damaohongtu.orderquery.dto.config.OrderQueryConfigDto;
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
    private GraphRepo graphRepo;

    @Resource
    private NodeRepo nodeRepo;

    public String config(OrderQueryConfigDto OrderQueryConfigDto){

        Graph graph = new Graph();
        graph.setGraphCode(OrderQueryConfigDto.getBizCode());
        graph.setGraphName(OrderQueryConfigDto.getBizName());
        graph.setEdges(OrderQueryConfigDto.getEdges());
        graphRepo.insert(graph);

        for(OrderQueryConfigDto.Node node : OrderQueryConfigDto.getNodes()){
            Node nodePo = new Node();
            nodePo.setNodeCode(node.getId());
            nodePo.setGraphCode(OrderQueryConfigDto.getBizCode());
            nodePo.setNodeName(node.getNodeName());
            nodePo.setNodeType(node.getNodeType());
            nodePo.setDataSource(node.getConfigInfo().getDataSource());
            nodePo.setInputField(node.getConfigInfo().getInputField());
            nodePo.setOutputField(node.getConfigInfo().getOutputField());
            nodePo.setRouteRule(node.getConfigInfo().getRouteRule());
            nodePo.setNeighborNode(node.getConfigInfo().getRelations());
            nodePo.setCoordinate(node.getCoordinate());
            nodeRepo.insert(nodePo);
        }

        return "SUCCESS";
    }

    public OrderQueryConfigDto query(String graphCode){
        Graph graph = graphRepo.selectByCode(graphCode);
        List<Node> nodeList = nodeRepo.selectByGraph(graphCode);

        List<OrderQueryConfigDto.Node> nodes = new ArrayList<>();
        nodeList.forEach(item -> {
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
                .bizCode(graph.getGraphCode())
                .bizName(graph.getGraphName())
                .bizDesc("")
                .nodes(nodes)
                .edges(graph.getEdges())
                .build();

        return orderQueryConfigDto;
    }

}
