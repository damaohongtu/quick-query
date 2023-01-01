package com.damaohongtu.orderquery.client;

import com.damaohongtu.orderquery.dao.entity.Graph;
import com.damaohongtu.orderquery.dao.entity.Node;
import com.damaohongtu.orderquery.dao.repo.GraphRepo;
import com.damaohongtu.orderquery.dao.repo.NodeRepo;
import com.damaohongtu.orderquery.dto.client.OrderQueryRequest;
import com.damaohongtu.orderquery.dto.client.OrderQueryResponse;
import com.damaohongtu.orderquery.dto.context.OrderQueryContext;
import com.damaohongtu.orderquery.dto.graph.NodeDto;
import com.damaohongtu.orderquery.service.query.QueryService;
import com.damaohongtu.orderquery.service.route.RouteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author: 大袤宏图
 * FileName: OrderQueryClient
 * Description: OrderQuery对外查询入口
 */

@Service
public class OrderQueryClient {


    @Resource
    private GraphRepo graphRepo;

    @Resource
    private NodeRepo nodeRepo;

    @Resource
    private RouteService routeService;

    @Resource
    private QueryService queryService;

    public OrderQueryResponse query(OrderQueryRequest request){

        String graphCode = request.getBizCode();
        Graph graph = graphRepo.selectByCode(graphCode);
        List<Node> nodes = nodeRepo.selectByGraph(graphCode);
        String serialNo = request.getSerialNo();

        // 上下文
        OrderQueryContext context = OrderQueryContext.builder()
                .graphCode(graph.getGraphCode())
                .graphName(graph.getGraphName())
                .graph(this.parseGraph(nodes))
                .serialNo(serialNo)
                .process(new HashMap<>(8))
                .currentResults(new HashMap<>(8))
                .globalResults(new HashMap<>(8))
                .entryNodeList(new ArrayList<>())
                .nextNode(new LinkedList<>())
                .build();

        // 路由
        routeService.route(context);

        // 查询
        queryService.query(context);

        return context.toOrderQueryResponse();
    }

    private Map<String, NodeDto> parseGraph(List<Node> nodes){
        Map<String, NodeDto> graph = new HashMap<>(8);
        for (Node node : nodes){
            NodeDto nodeDto = NodeDto.fromPo(node);
            graph.put(node.getNodeCode(), nodeDto);
        }
        return graph;
    }

}
