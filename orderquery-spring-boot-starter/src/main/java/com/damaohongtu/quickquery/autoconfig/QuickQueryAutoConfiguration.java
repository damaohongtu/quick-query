package com.damaohongtu.quickquery.autoconfig;

import com.alibaba.druid.pool.DruidDataSource;
import com.damaohongtu.quickquery.autoconfig.property.QuickQueryConfig;
import com.damaohongtu.quickquery.autoconfig.property.QuickQueryDataSourceConfig;
import com.damaohongtu.quickquery.autoconfig.property.ClickHouseProperty;
import com.damaohongtu.quickquery.autoconfig.property.HiveProperty;
import com.damaohongtu.quickquery.autoconfig.property.MysqlProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.clickhouse.ClickHouseDataSource;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 大袤宏图
 * FileName: OrderQueryAutoConfiguration
 * Description: OrderQuery初始化: (1) 系统初始化；(2) 业务方数据源初始化
 */
@Configuration
@EnableConfigurationProperties(value = {QuickQueryConfig.class, QuickQueryDataSourceConfig.class})
public class QuickQueryAutoConfiguration {

    private static final String MYSQL_URL_SETTING = "?useUnicode=true&characterEncoding=UTF-8&useOldAliasMetadataBehavior=true&autoReconnect=true&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull&useSSL=false&nullCatalogMeansCurrent=true";

    @Bean(name = "internal.mysql")
    public DruidDataSource internalDruidDataSource(QuickQueryConfig config) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(config.getMysql().getUsername());
        dataSource.setPassword(config.getMysql().getPassword());
        dataSource.setUrl(config.getMysql().getUrl() + MYSQL_URL_SETTING);
        dataSource.setDriverClassName(config.getMysql().getDriverClassName());
        return dataSource;
    }

    @Bean(name = "external.mysql")
    public Map<String, List<DruidDataSource>> externalDruidDataSourceMap(QuickQueryDataSourceConfig config){
        Map<String, List<DruidDataSource>> dataSourceMap = new HashMap<>(8);
        Map<String, List<MysqlProperty>> mysqlConfigMap = config.getMysql();
        for (String key:mysqlConfigMap.keySet()){
            List<DruidDataSource> druidDataSourceList = new ArrayList<>(8);
            List<MysqlProperty> mysqlPropertyList = mysqlConfigMap.get(key);
            for(MysqlProperty mysqlProperty : mysqlPropertyList){
                DruidDataSource dataSource = new DruidDataSource();
                dataSource.setUsername(mysqlProperty.getUsername());
                dataSource.setPassword(mysqlProperty.getPassword());
                dataSource.setUrl(mysqlProperty.getUrl() + MYSQL_URL_SETTING);
                dataSource.setDriverClassName(mysqlProperty.getDriverClassName());
                druidDataSourceList.add(dataSource);
            }
            dataSourceMap.put(key, druidDataSourceList);
        }
        return dataSourceMap;
    }

    @Bean(name = "external.clickhouse")
    public Map<String, ClickHouseDataSource> externalClickHouseDataSourceMap(QuickQueryDataSourceConfig config){
        Map<String, ClickHouseDataSource> clickHouseDataSourceMap = new HashMap<String, ClickHouseDataSource>(8);
        Map<String, ClickHouseProperty> clickHouseConfigMap = config.getClickHouse();

        for(String key:clickHouseConfigMap.keySet()){
            ClickHouseProperty clickHouseProperty = clickHouseConfigMap.get(key);
            String url = clickHouseProperty.getUrl();
            ClickHouseProperties properties = new ClickHouseProperties();
            properties.setUser(clickHouseProperty.getUsername());
            properties.setPassword(clickHouseProperty.getPassword());
            ClickHouseDataSource clickHouseDataSource = new ClickHouseDataSource(url, properties);
            clickHouseDataSourceMap.put(key, clickHouseDataSource);
        }
        return clickHouseDataSourceMap;

    }

    @Bean(name = "external.hive")
    public Map<String, DruidDataSource> externalHiveDataSourceMap(QuickQueryDataSourceConfig config){

        Map<String, DruidDataSource> dataSourceMap = new HashMap<String, DruidDataSource>(8);

        Map<String, HiveProperty> hiveConfigMap = config.getHive();
        for (String key:hiveConfigMap.keySet()){
            HiveProperty hiveProperty = hiveConfigMap.get(key);
        }
        return dataSourceMap;
    }


}
