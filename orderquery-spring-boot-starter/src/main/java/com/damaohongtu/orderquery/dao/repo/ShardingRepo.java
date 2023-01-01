package com.damaohongtu.orderquery.dao.repo;

import com.damaohongtu.orderquery.dao.entity.Sharding;
import com.damaohongtu.orderquery.dao.entity.ShardingExample;
import com.damaohongtu.orderquery.dao.mapper.ShardingMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class ShardingRepo {

    @Resource
    private ShardingMapper shardingMapper;

    public int insert(Sharding sharding){
        return shardingMapper.insert(sharding);
    }

    public Sharding selectByCode(String code){
        ShardingExample example = new ShardingExample();
        example.createCriteria()
                .andCodeEqualTo(code);
        return shardingMapper.selectByExample(example).stream().findFirst().orElse(null);
    }

    public List<Sharding> queryAllSharding(){
        ShardingExample example = new ShardingExample();
        return shardingMapper.selectByExample(example);
    }


}
