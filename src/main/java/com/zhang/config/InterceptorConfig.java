package com.zhang.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/image/**").addResourceLocations("file:///D:/360MoveData/Users/22972/Desktop/%E6%AF%95%E4%B8%9A%E8%AE%BE%E8%AE%A1/%E6%88%91%E7%9A%84/%E5%AE%9E%E7%8E%B0%E4%BB%A3%E7%A0%81/spider/img/");
    }

}

