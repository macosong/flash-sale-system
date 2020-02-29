package com.example.demo.config;

import com.example.demo.Access.AccessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.util.List;

/**
 * @author: songqiang
 * @date: 2020/2/29
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {
    AccessInterceptor accessInterceptor;
    UserArgumentResolver userArgumentResolver;

    @Autowired
    public SpringMvcConfig(AccessInterceptor interceptor, UserArgumentResolver userArgumentResolver){
        this.accessInterceptor = interceptor;
        this.userArgumentResolver = userArgumentResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userArgumentResolver);
    }
}
