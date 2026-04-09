package com.tharun.rest.ad.config;

import com.tharun.rest.ad.error.RestClientErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Client configuration for REST template.
 * Sets up RestTemplate with custom error handling for HTTP clients.
 *
 * @since 1.0
 */
@Configuration
@ComponentScan (basePackages = "com.tharun.rest.ad.error")
public class ClientConfig {

    @Autowired
    private RestClientErrorHandler restClientErrorHandler;

    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(restClientErrorHandler);
        return restTemplate;
    }
}
