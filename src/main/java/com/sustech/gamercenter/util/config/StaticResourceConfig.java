package com.sustech.gamercenter.util.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;

@Configuration
public class StaticResourceConfig extends WebMvcConfigurerAdapter {

    private static final String STORAGE_PREFIX = System.getProperty("user.dir");
//    private static final Logger logger = LoggerFactory.getLogger(StaticResourceConfig.class);

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String location = File.separator + "src"
                + File.separator + "main"
                + File.separator + "resources"
                + File.separator + "static"
                + File.separator + "admin"
                + File.separator + "manual"
                + File.separator;
//        logger.error(STORAGE_PREFIX + location);

        registry.addResourceHandler("/admin/manual/**")
                .addResourceLocations("file:" + STORAGE_PREFIX + location);
        super.addResourceHandlers(registry);
    }

}