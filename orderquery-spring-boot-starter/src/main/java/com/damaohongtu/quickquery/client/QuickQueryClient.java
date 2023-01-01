package com.damaohongtu.quickquery.client;

import com.damaohongtu.quickquery.dao.entity.Graph;
import com.damaohongtu.quickquery.dao.entity.Node;
import com.damaohongtu.quickquery.dao.repo.GraphRepo;
import com.damaohongtu.quickquery.dao.repo.NodeRepo;
import com.damaohongtu.quickquery.dto.client.QuickQueryRequest;
import com.damaohongtu.quickquery.dto.client.QuickQueryResponse;
import com.damaohongtu.quickquery.dto.context.QuickQueryContext;
import com.damaohongtu.quickquery.dto.graph.NodeDto;
import com.damaohongtu.quickquery.service.query.QueryService;
import com.damaohongtu.quickquery.service.route.RouteService;
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
public class QuickQueryClient {


    @Resource
    private GraphRepo graphRepo;

    @Resource
    private NodeRepo nodeRepo;

    @Resource
    private RouteService routeService;

    @Resource
    private QueryService queryService;

    public QuickQueryResponse query(QuickQueryRequest request){

        String graphCode = request.getBizCode();
        Graph graph = graphRepo.selectByCode(graphCode);
        List<Node> nodes = nodeRepo.selectByGraph(graphCode);
        String serialNo = request.getSerialNo();

        // 上下文
        QuickQueryContext context = QuickQueryContext.builder()
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

        return context.toQuickQueryResponse();
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
