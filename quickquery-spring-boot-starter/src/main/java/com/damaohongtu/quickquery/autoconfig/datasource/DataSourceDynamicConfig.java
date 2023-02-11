package com.damaohongtu.quickquery.autoconfig.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.damaohongtu.quickquery.dao.entity.Source;
import com.damaohongtu.quickquery.dao.repo.SourceRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.clickhouse.ClickHouseDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class DataSourceDynamicConfig {

    @Resource
    private SourceRepo sourceRepo;

    private Map<String, List<DruidDataSource>> dataSourceMap = new HashMap<>(8);

    public void buildDataSource(){
        List<Source> sourceList = sourceRepo.list();

    }

    public void clear(){
        dataSourceMap.clear();
    }

}
