package com.damaohongtu.quickquery.dao.mapper;

import com.damaohongtu.quickquery.dao.entity.Source;
import com.damaohongtu.quickquery.dao.entity.SourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SourceMapper {
    long countByExample(SourceExample example);

    int deleteByExample(SourceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Source record);

    int insertSelective(Source record);

    List<Source> selectByExampleWithRowbounds(SourceExample example, RowBounds rowBounds);

    List<Source> selectByExample(SourceExample example);

    Source selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Source record, @Param("example") SourceExample example);

    int updateByExample(@Param("record") Source record, @Param("example") SourceExample example);

    int updateByPrimaryKeySelective(Source record);

    int updateByPrimaryKey(Source record);

    int batchInsert(@Param("list") List<Source> list);

    int batchInsertSelective(@Param("list") List<Source> list, @Param("selective") Source.Column ... selective);

    int upsert(Source record);

    int upsertSelective(Source record);

    int batchUpsert(@Param("list") List<Source> list);

    int batchUpsertSelective(@Param("list") List<Source> list, @Param("selective") Source.Column ... selective);
}