package com.damaohongtu.orderquery.dal.repo;

import com.damaohongtu.orderquery.executor.DataBaseExecutor;
import com.damaohongtu.orderquery.dal.entity.OrderQueryGraph;
import org.apache.commons.collections4.CollectionUtils;
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
 * FileName: OrderQueryGraphRepo
 * Description: OrderQueryGraphRepo
 */

@Repository
public class OrderQueryGraphRepo {

    @Resource
    private DataBaseExecutor dataBaseExecutor;

    public int insert(OrderQueryGraph OrderQueryGraph){
        String sql = String.format("INSERT INTO order_query_graph (graph_code, graph_name, edges) VALUES ('%s', '%s', '%s');",
                OrderQueryGraph.getGraphCode(), OrderQueryGraph.getGraphName(), OrderQueryGraph.getEdges());
        List<Map> graph = null;
        try {
            dataBaseExecutor.execute("internal.mysql", null, null, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 1;
    }

    public int update(){
        return 0;
    }

    public OrderQueryGraph selectByGraph(String graphCode){

        String sql = String.format("select * from order_query_graph where valid=1 and graph_code='%s';", graphCode);
        List<Map> graph = null;
        try {
            graph = dataBaseExecutor.execute("internal.mysql", null, null, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        OrderQueryGraph OrderQueryGraph = this.parseGraphFromDb(graph).get(0);

        return OrderQueryGraph;
    }

    public List<OrderQueryGraph> queryAllGraph(){

        String sql = "select * from order_query_graph where valid=1;";
        List<Map> graph = null;
        try {
            graph = dataBaseExecutor.execute("internal.mysql", null, null, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<OrderQueryGraph> OrderQueryGraphList = this.parseGraphFromDb(graph);

        return OrderQueryGraphList;
    }



    private List<OrderQueryGraph> parseGraphFromDb(List<Map> graph) {

        List<OrderQueryGraph> OrderQueryGraphList = new ArrayList<>();
        if(CollectionUtils.isEmpty(graph)){
            return OrderQueryGraphList;
        }

        for(Map row : graph){
            OrderQueryGraph OrderQueryGraph = new OrderQueryGraph();
            OrderQueryGraph.setGraphCode(String.valueOf(row.get("graph_code")));
            OrderQueryGraph.setGraphName(String.valueOf(row.get("graph_name")));
            OrderQueryGraph.setEdges(String.valueOf(row.get("edges")));
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                OrderQueryGraph.setUpdateTime(sdf.parse(String.valueOf(row.get("update_time"))));
                OrderQueryGraph.setCreateTime(sdf.parse(String.valueOf(row.get("create_time"))));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            OrderQueryGraphList.add(OrderQueryGraph);
        }

        return OrderQueryGraphList;
    }

}
