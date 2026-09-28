package com.example.app.config;

import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Registers the SiteMesh 3 filter.
 * Decorator mappings live in src/main/webapp/WEB-INF/sitemesh3.xml
 */
@Configuration
public class SiteMeshConfig {

    @Bean
    public FilterRegistrationBean<ConfigurableSiteMeshFilter> siteMeshFilter() {
        FilterRegistrationBean<ConfigurableSiteMeshFilter> bean =
                new FilterRegistrationBean<>(new ConfigurableSiteMeshFilter());
        bean.addUrlPatterns("/*");
        bean.setOrder(0);
        return bean;
    }
}
