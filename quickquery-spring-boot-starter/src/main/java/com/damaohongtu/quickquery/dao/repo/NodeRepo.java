package com.damaohongtu.quickquery.dao.repo;

import com.damaohongtu.quickquery.dao.entity.Node;
import com.damaohongtu.quickquery.dao.entity.NodeExample;
import com.damaohongtu.quickquery.dao.mapper.NodeMapper;
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

    public List<Node> selectByGraph(Long graphId){
        NodeExample example = new NodeExample();
        example.createCriteria().andGraphIdEqualTo(graphId);
        return nodeMapper.selectByExample(example);
    }

}
