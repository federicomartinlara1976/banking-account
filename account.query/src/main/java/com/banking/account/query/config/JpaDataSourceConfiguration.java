package com.banking.account.query.config;

import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		basePackages = {"com.banking.account.query.repository"},
		entityManagerFactoryRef = "jpaEntityManagerFactory",
		transactionManagerRef = "jpaTransactionManager")
public class JpaDataSourceConfiguration {

	@Bean(name="jpaDataSource")
	public DataSource dataSource(@Autowired JpaProperties c3P0Properties) throws PropertyVetoException {
		ComboPooledDataSource pooledDataSource = new ComboPooledDataSource();
		pooledDataSource.setDriverClass(c3P0Properties.getDriverClass());
		pooledDataSource.setUser(c3P0Properties.getUser());
		pooledDataSource.setPassword(c3P0Properties.getPassword());
		pooledDataSource.setJdbcUrl(c3P0Properties.getJdbcUrl());
		pooledDataSource.setMaxPoolSize(c3P0Properties.getMaxPoolSize());
		pooledDataSource.setMaxIdleTime(c3P0Properties.getMaxIdleTime());

		return pooledDataSource;
	}

	@Bean(name = "jpaEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean batchEntityManagerFactory(
			@Qualifier("jpaDataSource") DataSource dataSource, EntityManagerFactoryBuilder builder) {
		Map<String, String> properties = new HashMap<>();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		
		return builder.dataSource(dataSource)
				.properties(properties)
				.persistenceUnit("jpa-unit")
				.build();
	}

	@Bean(name = "jpaTransactionManager")
	public PlatformTransactionManager batchTransactionManager(
			@Qualifier("jpaEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
