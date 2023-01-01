package com.damaohongtu.quickquery.dao.repo;

import com.damaohongtu.quickquery.dao.entity.Graph;
import com.damaohongtu.quickquery.dao.entity.GraphExample;
import com.damaohongtu.quickquery.dao.mapper.GraphMapper;
import com.damaohongtu.quickquery.util.Constants;
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

    /**
     * 计数
     * @return
     */
    public Long count(){
        GraphExample example = new GraphExample();
        example.createCriteria().andValidEqualTo(Constants.VALID);
        return graphMapper.countByExample(example);
    }

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    public List<Graph> list(Integer page, Integer size){
        GraphExample example = new GraphExample();
        example.createCriteria().andValidEqualTo(Constants.VALID);
        example.setOrderByClause("id DESC");
        example.limit((page-1) * size, size);
        return graphMapper.selectByExample(example);
    }


}
