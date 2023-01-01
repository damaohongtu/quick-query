package com.damaohongtu.orderquery.dao.mapper;

import com.damaohongtu.orderquery.dao.entity.Sharding;
import com.damaohongtu.orderquery.dao.entity.ShardingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ShardingMapper {
    long countByExample(ShardingExample example);

    int deleteByExample(ShardingExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Sharding record);

    int insertSelective(Sharding record);

    List<Sharding> selectByExampleWithBLOBsWithRowbounds(ShardingExample example, RowBounds rowBounds);

    List<Sharding> selectByExampleWithBLOBs(ShardingExample example);

    List<Sharding> selectByExampleWithRowbounds(ShardingExample example, RowBounds rowBounds);

    List<Sharding> selectByExample(ShardingExample example);

    Sharding selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Sharding record, @Param("example") ShardingExample example);

    int updateByExampleWithBLOBs(@Param("record") Sharding record, @Param("example") ShardingExample example);

    int updateByExample(@Param("record") Sharding record, @Param("example") ShardingExample example);

    int updateByPrimaryKeySelective(Sharding record);

    int updateByPrimaryKeyWithBLOBs(Sharding record);

    int updateByPrimaryKey(Sharding record);

    int batchInsert(@Param("list") List<Sharding> list);

    int batchInsertSelective(@Param("list") List<Sharding> list, @Param("selective") Sharding.Column ... selective);

    int upsert(Sharding record);

    int upsertSelective(Sharding record);

    int upsertWithBLOBs(Sharding record);

    int batchUpsert(@Param("list") List<Sharding> list);

    int batchUpsertSelective(@Param("list") List<Sharding> list, @Param("selective") Sharding.Column ... selective);

    int batchUpsertWithBLOBs(@Param("list") List<Sharding> list);
}