package com.damaohongtu.orderquery.dal.repo;

import com.damaohongtu.orderquery.executor.DataBaseExecutor;
import com.damaohongtu.orderquery.dal.entity.OrderQueryHash;
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
 * FileName: OrderQueryHashRepo
 * Description: 分库分表策略
 */

@Repository
public class OrderQueryHashRepo {

    @Resource
    private DataBaseExecutor dataBaseExecutor;

    public int insert(OrderQueryHash OrderQueryHash){
        String sql = String.format("INSERT INTO order_query_hash (code, name, script) " +
                        "VALUES ('%s','%s','%s');",
                OrderQueryHash.getCode(), OrderQueryHash.getName(), OrderQueryHash.getScript());
        List<Map> graph = null;
        try {
            dataBaseExecutor.execute("internal.mysql", null, null, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 1;
    }

    public OrderQueryHash select(String code){

        String sql = String.format("select * from order_query_hash where valid=1 and code='%s';", code);
        List<Map> rules = null;
        try {
            rules = dataBaseExecutor.execute("internal.mysql", null, null, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        OrderQueryHash OrderQueryHash = this.parseHashFromDb(rules).get(0);

        return OrderQueryHash;
    }

    public List<OrderQueryHash> queryAll(){

        String sql = "select * from order_query_hash where valid=1;";
        List<Map> rules = null;
        try {
            rules = dataBaseExecutor.execute("internal.mysql", null, null, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<OrderQueryHash> OrderQueryHashList = this.parseHashFromDb(rules);

        return OrderQueryHashList;
    }



    private List<OrderQueryHash> parseHashFromDb(List<Map> graph) {

        List<OrderQueryHash> OrderQueryHashList = new ArrayList<>();
        if(CollectionUtils.isEmpty(graph)){
            return OrderQueryHashList;
        }

        for(Map row : graph){
            OrderQueryHash OrderQueryHash = new OrderQueryHash();
            OrderQueryHash.setCode(String.valueOf(row.get("code")));
            OrderQueryHash.setName(String.valueOf(row.get("name")));
            OrderQueryHash.setScript(String.valueOf(row.get("script")));
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                OrderQueryHash.setUpdateTime(sdf.parse(String.valueOf(row.get("update_time"))));
                OrderQueryHash.setCreateTime(sdf.parse(String.valueOf(row.get("create_time"))));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            OrderQueryHashList.add(OrderQueryHash);

        }

        return OrderQueryHashList;
    }

}
