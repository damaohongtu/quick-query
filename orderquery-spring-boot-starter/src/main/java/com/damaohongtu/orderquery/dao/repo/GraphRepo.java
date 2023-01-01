package com.damaohongtu.orderquery.dao.repo;

import com.damaohongtu.orderquery.dao.entity.Graph;
import com.damaohongtu.orderquery.dao.entity.GraphExample;
import com.damaohongtu.orderquery.dao.mapper.GraphMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class GraphRepo {

    @Resource
    private GraphMapper graphMapper;


    public int insert(Graph graph){
        return graphMapper.insert(graph);
    }


    public Graph selectByCode(String graphCode){

        GraphExample example = new GraphExample();
        example.createCriteria().andGraphCodeEqualTo(graphCode);
        return graphMapper.selectByExample(example).stream().findFirst().orElse(null);
    }

    public List<Graph> queryAllGraph(){
        GraphExample example = new GraphExample();
        return graphMapper.selectByExample(example);
    }

}
