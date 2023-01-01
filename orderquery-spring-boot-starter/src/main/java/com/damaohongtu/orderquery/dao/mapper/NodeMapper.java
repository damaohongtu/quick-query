package com.damaohongtu.orderquery.dao.mapper;

import com.damaohongtu.orderquery.dao.entity.Node;
import com.damaohongtu.orderquery.dao.entity.NodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface NodeMapper {
    long countByExample(NodeExample example);

    int deleteByExample(NodeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Node record);

    int insertSelective(Node record);

    List<Node> selectByExampleWithBLOBsWithRowbounds(NodeExample example, RowBounds rowBounds);

    List<Node> selectByExampleWithBLOBs(NodeExample example);

    List<Node> selectByExampleWithRowbounds(NodeExample example, RowBounds rowBounds);

    List<Node> selectByExample(NodeExample example);

    Node selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Node record, @Param("example") NodeExample example);

    int updateByExampleWithBLOBs(@Param("record") Node record, @Param("example") NodeExample example);

    int updateByExample(@Param("record") Node record, @Param("example") NodeExample example);

    int updateByPrimaryKeySelective(Node record);

    int updateByPrimaryKeyWithBLOBs(Node record);

    int updateByPrimaryKey(Node record);

    int batchInsert(@Param("list") List<Node> list);

    int batchInsertSelective(@Param("list") List<Node> list, @Param("selective") Node.Column ... selective);

    int upsert(Node record);

    int upsertSelective(Node record);

    int upsertWithBLOBs(Node record);

    int batchUpsert(@Param("list") List<Node> list);

    int batchUpsertSelective(@Param("list") List<Node> list, @Param("selective") Node.Column ... selective);

    int batchUpsertWithBLOBs(@Param("list") List<Node> list);
}