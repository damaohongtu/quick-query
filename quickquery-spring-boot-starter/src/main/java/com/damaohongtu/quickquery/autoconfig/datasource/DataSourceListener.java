package com.damaohongtu.quickquery.autoconfig.datasource;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import org.springframework.core.annotation.Order;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@AllArgsConstructor
public class DataSourceListener  implements ApplicationListener<ApplicationStartedEvent> {

    private DataSourceDynamicConfig dataSourceDynamicConfig;

    @SneakyThrows
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        this.dataSourceDynamicConfig.buildDataSource();
    }
}
