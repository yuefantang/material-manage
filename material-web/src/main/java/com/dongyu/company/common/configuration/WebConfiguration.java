package com.dongyu.company.common.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * spring web配置类
 *
 * @author TYF
 * @date 2018/11/8
 * @since 1.0.0
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurationSupport {


    /**
     * 拦截器配置
     *
     * @param registry 注册类
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    }

    /**
     * 静态资源
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //swagger
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

        //前端


    }

}
