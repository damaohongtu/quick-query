package com.damaohongtu.orderquery.dao.repo;

import com.damaohongtu.orderquery.dao.entity.Node;
import com.damaohongtu.orderquery.dao.entity.NodeExample;
import com.damaohongtu.orderquery.dao.mapper.NodeMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class NodeRepo {

    @Resource
    private NodeMapper nodeMapper;

    public int insert(Node node){
        return nodeMapper.insert(node);
    }

    public List<Node> selectByGraph(String graphCode){
        NodeExample example = new NodeExample();
        example.createCriteria().andGraphCodeEqualTo(graphCode);
        return nodeMapper.selectByExample(example);
    }

}
