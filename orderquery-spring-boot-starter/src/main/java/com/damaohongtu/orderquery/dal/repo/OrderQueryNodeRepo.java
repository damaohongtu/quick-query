package com.damaohongtu.orderquery.dal.repo;

import com.damaohongtu.orderquery.executor.DataBaseExecutor;
import com.damaohongtu.orderquery.dal.entity.OrderQueryNode;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: 大袤宏图
 * FileName: OrderQueryNodeRepo
 * Description: OrderQueryNodeRepo
 */

@Repository
public class OrderQueryNodeRepo {

    @Resource
    private DataBaseExecutor dataBaseExecutor;

    public int insert(OrderQueryNode OrderQueryNode){
        String sql = String.format("INSERT INTO order_query_node (" +
                        "node_code, graph_code, node_name, " +
                        "node_type, data_source, input_field, " +
                        "output_field, route_rule, neighbor_node, " +
                        "coordinate) " +
                        "VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s');",
                OrderQueryNode.getNodeCode(), OrderQueryNode.getGraphCode(), OrderQueryNode.getNodeName(), OrderQueryNode.getNodeType(),
                OrderQueryNode.getDataSource(), OrderQueryNode.getInputField(), OrderQueryNode.getOutputField(), OrderQueryNode.getRouteRule(), OrderQueryNode.getNeighborNode(), OrderQueryNode.getCoordinate());
        List<Map> graph = null;
        try {
            dataBaseExecutor.execute("internal.mysql", null, null, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 1;
    }

    public List<OrderQueryNode> selectByGraph(String graphCode){

        String sql = String.format("select * from order_query_node where valid=1 and graph_code='%s';", graphCode);
        List<Map> graph = null;
        try {
            graph = dataBaseExecutor.execute("internal.mysql", null, null, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<OrderQueryNode> OrderQueryNodes = this.parseGraphFromDb(graph);

        return OrderQueryNodes;
    }

    /**
     * 将关联图配置进行查询
     * @param graph
     * @return
     */
    private List<OrderQueryNode> parseGraphFromDb(List<Map> graph) {
        List<OrderQueryNode> result = new ArrayList<>();
        for(Map row : graph){
            OrderQueryNode node = new OrderQueryNode();
            node.setNodeCode(String.valueOf(row.get("node_code")));
            node.setGraphCode(String.valueOf(row.get("graph_code")));
            node.setNodeName(String.valueOf(row.get("node_name")));
            node.setNodeType(String.valueOf(String.valueOf(row.get("node_type"))));
            node.setDataSource(String.valueOf(row.get("data_source")));
            node.setInputField(String.valueOf(row.get("input_field")));
            node.setOutputField(String.valueOf(row.get("output_field")));
            node.setRouteRule(String.valueOf(row.get("route_rule")));
            node.setNeighborNode(String.valueOf(row.get("neighbor_node")));
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                node.setUpdateTime(sdf.parse(String.valueOf(row.get("update_time"))));
                node.setCreateTime(sdf.parse(String.valueOf(row.get("create_time"))));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            node.setCoordinate(String.valueOf(row.get("coordinate")));
            node.setValid(Byte.valueOf(String.valueOf(row.get("valid"))));
            result.add(node);
        }
        return result;
    }


}
