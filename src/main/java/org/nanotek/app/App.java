package org.nanotek.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class App {

	public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
	
	@Bean
	public AppRunner appRunner() { 
		return new AppRunner() ;
	}
	
}
