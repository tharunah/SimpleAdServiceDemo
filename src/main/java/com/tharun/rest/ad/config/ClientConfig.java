package com.tharun.rest.ad.config;

import com.tharun.rest.ad.error.RestClientErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by tharu on 4/27/2017.
 */
@Configuration
@ComponentScan (basePackages = "com.tharun.rest.ad.error")
public class ClientConfig {

    @Autowired
    RestClientErrorHandler restClientErrorHandler;

    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(restClientErrorHandler);
        return restTemplate;
    }
}
