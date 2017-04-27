package com.tharun.rest.ad.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * Created by tharu on 4/27/2017.
 */
@Configuration
@ComponentScan(basePackages = {"com.tharun.rest.ad"},
        excludeFilters = {@ComponentScan.Filter(Configuration.class),
                @ComponentScan.Filter(Controller.class),
                @ComponentScan.Filter(ControllerAdvice.class)
        })
public class MainConfig {
}
