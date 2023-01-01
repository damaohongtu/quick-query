package com.damaohongtu.quickquery.autoconfig;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * @author: 大袤宏图
 * FileName: DataSourceConfig
 * Description: 系统数据源配置
 */

@Configuration
@MapperScan(basePackages = "com.damaohongtu.orderquery.dao.mapper", sqlSessionFactoryRef = "configSqlSessionFactory")
public class DataSourceConfig {

    static final String MAPPER_LOCATION = "classpath:mapper/*Mapper.xml";

    @Value("${order-query.internal.datasource.mysql.url}")
    private String url;

    @Value("${order-query.internal.datasource.mysql.username}")
    private String username;

    @Value("${order-query.internal.datasource.mysql.password}")
    private String password;

    @Value("${order-query.internal.datasource.mysql.driver-class-name}")
    private String driverClassName;

    /**
     * 数据源
     */
    @Bean(name = "configDataSource")
    @Primary
    public DataSource configDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "configTransactionManager")
    @Primary
    public DataSourceTransactionManager configTransactionManager() {
        return new DataSourceTransactionManager(configDataSource());
    }

    /**
     * 编程式事务模板
     */
    @Bean(name = "configTransactionTemplate")
    public TransactionTemplate configTransactionTemplate() {
        return new TransactionTemplate(configTransactionManager());
    }

    /**
     * SqlSession工厂
     */
    @Bean(name = "configSqlSessionFactory")
    @Primary
    public SqlSessionFactory configSqlSessionFactory(@Qualifier("configDataSource") DataSource configDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(configDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}