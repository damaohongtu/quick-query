package com.damaohongtu.orderquery.dao.repo;

import com.damaohongtu.orderquery.dao.mapper.ShardingMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class ShardingRepo {

    @Resource
    private ShardingMapper shardingMapper;
}
