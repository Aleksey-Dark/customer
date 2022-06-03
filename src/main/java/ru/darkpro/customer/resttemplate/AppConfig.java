package ru.darkpro.customer.resttemplate;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@EnableAutoConfiguration
@ComponentScan("ru.darkpro.customer.resttemplate")
public class AppConfig {

    @Bean
    @Qualifier("customRestTemplate")
    public CustomRestTemplate customRestTemplate() {
        return new CustomRestTemplate();
    }

    @Bean
    @DependsOn(value = {"customRestTemplate"})
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder(customRestTemplate());
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
