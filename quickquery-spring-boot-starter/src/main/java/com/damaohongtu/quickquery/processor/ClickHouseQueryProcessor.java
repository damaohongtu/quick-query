package com.damaohongtu.quickquery.processor;

import com.damaohongtu.quickquery.dto.data.Condition;
import com.damaohongtu.quickquery.dto.graph.NodeDto;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class ClickHouseQueryProcessor extends QueryProcessor {

    @Resource
    private com.damaohongtu.quickquery.executor.DataBaseExecutor DataBaseExecutor;


    @Override
    public List<Map> fetch(NodeDto nodeDTO, Condition condition) {
        return null;
    }
}
