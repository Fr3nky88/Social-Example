package it.develhope.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableAutoConfiguration
public class SocialApplication {

	@Bean
	public ModelMapper getMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SocialApplication.class, args);
	}

}
