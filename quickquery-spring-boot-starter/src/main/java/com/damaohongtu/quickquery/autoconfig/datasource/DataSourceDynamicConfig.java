package com.damaohongtu.quickquery.autoconfig.datasource;

import com.damaohongtu.quickquery.dao.repo.SourceRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Map;

@Component
@AllArgsConstructor
public class DataSourceDynamicConfig {

    @Resource
    private SourceRepo sourceRepo;

    private final Map<String, DataSource> mysqlDataSourceMap;

    public void buildDataSource(){

    }

}
