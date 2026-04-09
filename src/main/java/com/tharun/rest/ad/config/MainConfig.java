package com.tharun.rest.ad.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * Main application configuration.
 * Enables component scanning for services and beans (excluding controllers).
 *
 * @since 1.0
 */
@Configuration
@ComponentScan(basePackages = {"com.tharun.rest.ad"},
        excludeFilters = {@ComponentScan.Filter(Configuration.class),
                @ComponentScan.Filter(Controller.class),
                @ComponentScan.Filter(ControllerAdvice.class)
        })
public class MainConfig {
}
