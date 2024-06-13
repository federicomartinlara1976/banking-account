package com.banking.account.query.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "c3p0")
public class JpaProperties {
    
	@Getter
    @Setter
    private int minPoolSize;
    
	@Getter
    @Setter
    private int maxPoolSize;
    
	@Getter
    @Setter
    private int maxIdleTime;
    
	@Getter
    @Setter
    private String driverClass;
    
	@Getter
    @Setter
    private String password;
    
	@Getter
    @Setter
    private String user;
    
	@Getter
    @Setter
    private String jdbcUrl;
}