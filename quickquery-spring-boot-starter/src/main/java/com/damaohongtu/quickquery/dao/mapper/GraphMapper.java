package com.damaohongtu.quickquery.dao.mapper;

import com.damaohongtu.quickquery.dao.entity.Graph;
import com.damaohongtu.quickquery.dao.entity.GraphExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface GraphMapper {
    long countByExample(GraphExample example);

    int deleteByExample(GraphExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Graph record);

    int insertSelective(Graph record);

    List<Graph> selectByExampleWithBLOBsWithRowbounds(GraphExample example, RowBounds rowBounds);

    List<Graph> selectByExampleWithBLOBs(GraphExample example);

    List<Graph> selectByExampleWithRowbounds(GraphExample example, RowBounds rowBounds);

    List<Graph> selectByExample(GraphExample example);

    Graph selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Graph record, @Param("example") GraphExample example);

    int updateByExampleWithBLOBs(@Param("record") Graph record, @Param("example") GraphExample example);

    int updateByExample(@Param("record") Graph record, @Param("example") GraphExample example);

    int updateByPrimaryKeySelective(Graph record);

    int updateByPrimaryKeyWithBLOBs(Graph record);

    int updateByPrimaryKey(Graph record);

    int batchInsert(@Param("list") List<Graph> list);

    int batchInsertSelective(@Param("list") List<Graph> list, @Param("selective") Graph.Column ... selective);

    int upsert(Graph record);

    int upsertSelective(Graph record);

    int upsertWithBLOBs(Graph record);

    int batchUpsert(@Param("list") List<Graph> list);

    int batchUpsertSelective(@Param("list") List<Graph> list, @Param("selective") Graph.Column ... selective);

    int batchUpsertWithBLOBs(@Param("list") List<Graph> list);
}