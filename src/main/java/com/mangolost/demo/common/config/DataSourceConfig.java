package com.mangolost.demo.common.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by mangolost on 2017-09-11
 */
@Configuration
public class DataSourceConfig {

	@Bean("primaryDataSource")
	@ConfigurationProperties(prefix = "primary.datasource")
	public DataSource primaryDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean("primaryJdbcTemplate")
	public JdbcTemplate primaryJdbcTemplate(
			@Qualifier("primaryDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}
