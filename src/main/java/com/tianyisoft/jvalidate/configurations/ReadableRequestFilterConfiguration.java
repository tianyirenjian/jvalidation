package com.tianyisoft.jvalidate.configurations;

import com.tianyisoft.jvalidate.filters.ReadableRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReadableRequestFilterConfiguration {
    private final ReadableRequestFilter filter;

    public ReadableRequestFilterConfiguration(ReadableRequestFilter filter) {
        this.filter = filter;
    }

    @Bean
    public FilterRegistrationBean<ReadableRequestFilter> registerFilter() {
        FilterRegistrationBean<ReadableRequestFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(filter);
        bean.addUrlPatterns("/*");
        bean.setName("readableRequestFilter");
        bean.setOrder(1);
        return bean;
    }
}
