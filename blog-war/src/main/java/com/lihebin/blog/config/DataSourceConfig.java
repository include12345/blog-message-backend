package com.lihebin.blog.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by lihebin on 2018/12/2.
 */
@Configuration
public class DataSourceConfig {

    @Bean(name= "backendDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.backend")
    public DataSource backendDataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }


    @Bean(name = "backendJdbcTemplate")
    public JdbcTemplate backendJdbcTemplate(@Qualifier("backendDataSource") DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setQueryTimeout(3);
        return jdbcTemplate;
    }
}
