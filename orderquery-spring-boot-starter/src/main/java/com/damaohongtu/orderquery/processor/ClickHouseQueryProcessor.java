package com.damaohongtu.orderquery.processor;

import com.damaohongtu.orderquery.dto.data.Condition;
import com.damaohongtu.orderquery.dto.graph.NodeDto;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class ClickHouseQueryProcessor extends QueryProcessor {

    @Resource
    private com.damaohongtu.orderquery.executor.DataBaseExecutor DataBaseExecutor;


    @Override
    public List<Map> fetch(NodeDto nodeDTO, Condition condition) {
        return null;
    }
}
