package com.damaohongtu.quickquery.dao.repo;

import com.damaohongtu.quickquery.dao.entity.Source;
import com.damaohongtu.quickquery.dao.entity.SourceExample;
import com.damaohongtu.quickquery.dao.mapper.SourceMapper;
import com.damaohongtu.quickquery.util.Constants;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class SourceRepo {

    @Resource
    private SourceMapper sourceMapper;

    public List<Source> list(){
        SourceExample example = new SourceExample();
        example.createCriteria().andValidEqualTo(Constants.VALID);
        return sourceMapper.selectByExample(example);
    }

}
