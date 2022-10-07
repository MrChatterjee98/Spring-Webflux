package com.spring.reactive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;

@EnableR2dbcAuditing
@Configuration
public class DbConfiguration {


	    @Bean
	    public ConnectionFactory connectionFactory() {
	        return ConnectionFactories.get(
	                ConnectionFactoryOptions.builder()
	                .option(ConnectionFactoryOptions.DRIVER, "postgres")
	                .option(ConnectionFactoryOptions.HOST, "localhost")
	                .option(ConnectionFactoryOptions.DATABASE, "postgres")
	                .option(ConnectionFactoryOptions.USER, "postgres")
	                .option(ConnectionFactoryOptions.PASSWORD, "12345")
	                .build());
	                        
	    

	}
}
