package com.damaohongtu.orderquery.client;

import com.damaohongtu.orderquery.dto.client.OrderQueryRequest;
import com.damaohongtu.orderquery.dto.client.OrderQueryResponse;
import com.damaohongtu.orderquery.dto.context.OrderQueryContext;
import com.damaohongtu.orderquery.dto.graph.Node;
import com.damaohongtu.orderquery.dal.entity.OrderQueryGraph;
import com.damaohongtu.orderquery.dal.entity.OrderQueryNode;
import com.damaohongtu.orderquery.dal.repo.OrderQueryGraphRepo;
import com.damaohongtu.orderquery.dal.repo.OrderQueryNodeRepo;
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
    private RouteService routeService;

    @Resource
    private QueryService queryService;

    @Resource
    private OrderQueryNodeRepo OrderQueryNodeRepo;

    @Resource
    private OrderQueryGraphRepo OrderQueryGraphRepo;

    public OrderQueryResponse query(OrderQueryRequest request){

        String graphCode = request.getBizCode();
        OrderQueryGraph OrderQueryGraph = OrderQueryGraphRepo.selectByGraph(graphCode);
        List<OrderQueryNode> OrderQueryNodes = OrderQueryNodeRepo.selectByGraph(graphCode);
        String serialNo = request.getSerialNo();

        // 上下文
        OrderQueryContext context = OrderQueryContext.builder()
                .graphCode(OrderQueryGraph.getGraphCode())
                .graphName(OrderQueryGraph.getGraphName())
                .graph(this.parseGraph(OrderQueryNodes))
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

    private Map<String, Node> parseGraph(List<OrderQueryNode> OrderQueryNodes){
        Map<String, Node> graph = new HashMap<>(8);
        for (OrderQueryNode node : OrderQueryNodes){
            Node nodeDto = Node.fromDo(node);
            graph.put(node.getNodeCode(), nodeDto);
        }
        return graph;
    }

}
