package com.damaohongtu.orderquery.dao.repo;

import com.damaohongtu.orderquery.dao.mapper.GraphMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class GraphRepo {

    @Resource
    private GraphMapper graphMapper;

}
