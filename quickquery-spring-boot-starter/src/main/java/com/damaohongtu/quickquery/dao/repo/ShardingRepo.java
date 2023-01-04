package com.damaohongtu.quickquery.dao.repo;

import com.damaohongtu.quickquery.dao.entity.Sharding;
import com.damaohongtu.quickquery.dao.entity.ShardingExample;
import com.damaohongtu.quickquery.dao.mapper.ShardingMapper;
import com.damaohongtu.quickquery.util.Constants;
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

    public List<Sharding> list(Integer page, Integer size){
        ShardingExample example = new ShardingExample();
        example.createCriteria()
                .andValidEqualTo(Constants.VALID);
        example.setOrderByClause("id desc");
        example.limit((page-1)*size, size);
        return shardingMapper.selectByExample(example);
    }

    public Long count(){
        ShardingExample example = new ShardingExample();
        example.createCriteria().andValidEqualTo(Constants.VALID);
        return shardingMapper.countByExample(example);
    }


}
