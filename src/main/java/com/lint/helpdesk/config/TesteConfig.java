package com.lint.helpdesk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.lint.helpdesk.services.DbService;

@Configuration
@Profile("test")
public class TesteConfig {
	
	@Autowired
	private DbService dbService;
	
	@Bean
	public void instanciaDB() {
		this.dbService.instanciaDB();
	}

}
