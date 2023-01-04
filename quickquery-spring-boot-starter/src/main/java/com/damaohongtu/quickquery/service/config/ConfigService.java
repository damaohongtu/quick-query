package com.damaohongtu.quickquery.service.config;

import com.damaohongtu.quickquery.controller.response.PageInfo;
import com.damaohongtu.quickquery.dao.entity.Graph;
import com.damaohongtu.quickquery.dao.entity.Node;
import com.damaohongtu.quickquery.dao.entity.Sharding;
import com.damaohongtu.quickquery.dao.repo.GraphRepo;
import com.damaohongtu.quickquery.dao.repo.NodeRepo;
import com.damaohongtu.quickquery.dao.repo.ShardingRepo;
import com.damaohongtu.quickquery.dto.config.QuickQueryConfigDto;
import com.damaohongtu.quickquery.dto.graph.GraphDto;
import com.damaohongtu.quickquery.dto.sharding.ShardingDto;
import com.damaohongtu.quickquery.executor.DataBaseExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @Resource
    private ShardingRepo shardingRepo;

    @Resource
    private DataBaseExecutor dataBaseExecutor;

    public String configGraph(QuickQueryConfigDto QuickQueryConfigDto){

        Graph graph = new Graph();
        graph.setGraphName(QuickQueryConfigDto.getBizName());
        graph.setEdges(QuickQueryConfigDto.getEdges());
        graphRepo.insert(graph);

        for(QuickQueryConfigDto.Node node : QuickQueryConfigDto.getNodes()){
            Node nodePo = new Node();
            nodePo.setNodeCode(node.getId());
            nodePo.setGraphId(graph.getId());
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

    public QuickQueryConfigDto queryGraph(Long graphId){
        Graph graph = graphRepo.selectById(graphId);
        List<Node> nodeList = nodeRepo.selectByGraph(graphId);

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
                .bizCode(graph.getId())
                .bizName(graph.getGraphName())
                .bizDesc(graph.getGraphDesc())
                .nodes(nodes)
                .edges(graph.getEdges())
                .build();

        return quickQueryConfigDto;
    }

    public PageInfo<GraphDto> listGraph(Integer page, Integer size){
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

    public PageInfo<ShardingDto> listSharding(Integer page, Integer size){
        Long total = shardingRepo.count();
        List<Sharding> shardingList = shardingRepo.list(page, size);
        List<ShardingDto> shardingDtoList = shardingList.stream().map(item->{
            return ShardingDto.fromPo(item);
        }).collect(Collectors.toList());
        PageInfo<ShardingDto> pageInfo = PageInfo.<ShardingDto>builder()
                .total(total)
                .data(shardingDtoList)
                .build();
        return pageInfo;
    }

    public PageInfo<ShardingDto> listSharding(){
        Long total = shardingRepo.count();
        List<Sharding> shardingList = shardingRepo.list(1, Math.toIntExact(total));
        List<ShardingDto> shardingDtoList = shardingList.stream().map(item->{
            return ShardingDto.fromPo(item);
        }).collect(Collectors.toList());
        PageInfo<ShardingDto> pageInfo = PageInfo.<ShardingDto>builder()
                .total(total)
                .data(shardingDtoList)
                .build();
        return pageInfo;
    }

    public String configSharding(ShardingDto shardingDto){
        Sharding sharding = shardingDto.toPo();
        shardingRepo.insert(sharding);
        return "SUCCESS";
    }

    public ShardingDto querySharding(String code){
        Sharding sharding = shardingRepo.selectByCode(code);
        return ShardingDto.fromPo(sharding);
    }

    public Map<String, List<String>> listDatabase(){
        return dataBaseExecutor.listDatabase();
    }

    public List queryTableInfo(String dataSourceType, String dataSourceName, String table){
        return dataBaseExecutor.getTableInfo(dataSourceType, dataSourceName, table);
    }
}
