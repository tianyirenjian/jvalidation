package com.tianyisoft.jvalidate.filters;

import com.tianyisoft.jvalidate.utils.ReadableRequestWrapper;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class ReadableRequestFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(new ReadableRequestWrapper((HttpServletRequest) servletRequest), servletResponse);
    }
}
