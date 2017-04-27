package com.tharun.rest.ad.initializer;

import com.tharun.rest.ad.config.ClientConfig;
import com.tharun.rest.ad.config.MainConfig;
import com.tharun.rest.ad.config.WebMvcConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Created by tharu on 4/27/2017.
 */
public class SpringWebMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{MainConfig.class, ClientConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebMvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
