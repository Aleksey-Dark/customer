package ru.darkpro.customer;

import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
//import ru.darkpro.customer.RestTemplate.CustomRestTemplate;

@EnableAsync
@SpringBootApplication
//@EnableAutoConfiguration
public class CustomerApplication {

//	private static final Logger log = (Logger) LoggerFactory.getLogger(CustomerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}

}
