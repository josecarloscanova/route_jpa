package org.nanotek.app;

import org.nanotek.app.util.DestinationComparator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan
@EntityScan(basePackages={"org.nanotek.model.jpa"})
@EnableJpaRepositories(basePackages={"org.nanotek.app.service.repository"})
@SpringBootApplication
public class App {

	public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
	
	@Bean
	public AppRunner appRunner() { 
		return new AppRunner() ;
	}
	
	@Bean
	public DestinationComparator destinationComparator() { 
		return new DestinationComparator();
	}
	
}
