package com.shoppingflightoffers;

import javax.validation.Validator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
@RefreshScope
//@EnableResourceServer
@EnableMongoAuditing
@SuppressWarnings("checkstyle:designforextension")
public class ShoppingFlightOffersApplication {
    
    public static void main(final String... args) {
        SpringApplication.run(ShoppingFlightOffersApplication.class, args);
    }
    
//    @Bean
//    public Validator validator(final MessageSource messageSource) {
//        final LocalValidatorFactoryBean validatorFactory = new LocalValidatorFactoryBean();
//        validatorFactory.setValidationMessageSource(messageSource);
//        
//        return validatorFactory;
//    }
}
