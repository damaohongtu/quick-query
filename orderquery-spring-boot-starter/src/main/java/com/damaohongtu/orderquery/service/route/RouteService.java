package com.damaohongtu.orderquery.service.route;

import com.damaohongtu.orderquery.dto.context.OrderQueryContext;
import com.damaohongtu.orderquery.dto.graph.NodeDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author: 大袤宏图
 * FileName: RouteService
 * Description: 路由
 */
@Service
public class RouteService {

    public void route(OrderQueryContext context){

        List<String> entryNodeList = new ArrayList<String>();
        List<String> allNodeCode = new ArrayList<>();

        Map<String, NodeDto> graph = context.getGraph();

        // 正则匹配路由
        for(String nodeCode : graph.keySet()){
            allNodeCode.add(nodeCode);
            NodeDto nodeDTO = graph.get(nodeCode);
            String rule = nodeDTO.getRouteRule();
            if(this.match(context.getSerialNo(), rule)){
                entryNodeList.add(nodeCode);
            }
        }

        // 返回
        if (entryNodeList.size() > 0){
            context.setEntryNodeList(entryNodeList);
        }else{
            context.setEntryNodeList(allNodeCode);
        }

    }

    /**
     * 正则表达式，路由规则匹配
     * @param serialNo
     * @param rule
     * @return
     */
    private boolean match(String serialNo, String rule){
        return Pattern.compile(rule).matcher(serialNo).matches();
    }

}
