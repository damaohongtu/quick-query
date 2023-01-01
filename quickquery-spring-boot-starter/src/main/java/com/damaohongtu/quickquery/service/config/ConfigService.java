package com.damaohongtu.quickquery.service.config;

import com.damaohongtu.quickquery.controller.response.PageInfo;
import com.damaohongtu.quickquery.dao.entity.Graph;
import com.damaohongtu.quickquery.dao.entity.Node;
import com.damaohongtu.quickquery.dao.repo.GraphRepo;
import com.damaohongtu.quickquery.dao.repo.NodeRepo;
import com.damaohongtu.quickquery.dto.config.QuickQueryConfigDto;
import com.damaohongtu.quickquery.dto.graph.GraphDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public String config(QuickQueryConfigDto QuickQueryConfigDto){

        Graph graph = new Graph();
        graph.setGraphCode(QuickQueryConfigDto.getBizCode());
        graph.setGraphName(QuickQueryConfigDto.getBizName());
        graph.setEdges(QuickQueryConfigDto.getEdges());
        graphRepo.insert(graph);

        for(QuickQueryConfigDto.Node node : QuickQueryConfigDto.getNodes()){
            Node nodePo = new Node();
            nodePo.setNodeCode(node.getId());
            nodePo.setGraphCode(QuickQueryConfigDto.getBizCode());
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

    public QuickQueryConfigDto query(String graphCode){
        Graph graph = graphRepo.selectByCode(graphCode);
        List<Node> nodeList = nodeRepo.selectByGraph(graphCode);

        List<QuickQueryConfigDto.Node> nodes = new ArrayList<>();
        nodeList.forEach(item -> {
            QuickQueryConfigDto.ConfigInfo configInfo = QuickQueryConfigDto.ConfigInfo.builder()
                    .dataSource(item.getDataSource())
                    .inputField(item.getInputField())
                    .outputField(item.getOutputField())
                    .routeRule(item.getRouteRule())
                    .relations(item.getNeighborNode())
                    .build();
            QuickQueryConfigDto.Node node = QuickQueryConfigDto.Node.builder()
                    .id(item.getNodeCode())
                    .nodeType(item.getNodeType())
                    .coordinate(item.getCoordinate())
                    .configInfo(configInfo)
                    .build();
            nodes.add(node);
        });

        QuickQueryConfigDto quickQueryConfigDto = QuickQueryConfigDto.builder()
                .bizCode(graph.getGraphCode())
                .bizName(graph.getGraphName())
                .bizDesc("")
                .nodes(nodes)
                .edges(graph.getEdges())
                .build();

        return quickQueryConfigDto;
    }

    public PageInfo<GraphDto> list(Integer page, Integer size){
        Long total = graphRepo.count();
        List<Graph> graphList = graphRepo.list(page, size);
        List<GraphDto> graphDtoList = graphList.stream().map(graph -> {
            return GraphDto.fromPo(graph);
        }).collect(Collectors.toList());

        PageInfo<GraphDto> res = PageInfo.<GraphDto>builder()
                .total(total)
                .data(graphDtoList)
                .build();
        return res;
    }

}
