package com.damaohongtu.orderquery.dao.repo;

import com.damaohongtu.orderquery.dao.mapper.NodeMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class NodeRepo {

    @Resource
    private NodeMapper nodeMapper;
}
