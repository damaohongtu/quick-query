package com.damaohongtu.orderquery.processor;

import com.damaohongtu.orderquery.dto.data.Condition;
import com.damaohongtu.orderquery.dto.graph.NodeDto;
import com.damaohongtu.orderquery.executor.HttpExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Component
public class HttpQueryProcessor extends QueryProcessor {
    @Resource
    private HttpExecutor httpExecutor;

    @Override
    public List<Map> fetch(NodeDto nodeDTO, Condition condition) {
        return null;
    }
}
