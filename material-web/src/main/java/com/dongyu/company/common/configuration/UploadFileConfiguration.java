package com.dongyu.company.common.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * 文件图片上传配置
 *
 * @author TYF
 * @date 2018/11/19
 * @since 1.0.0
 */
@Configuration
public class UploadFileConfiguration {
    @Value("${multipart.maxFileSize}")
    private String maxFileSize;

    @Value("${multipart.maxRequestSize}")
    private String maxRequestSize;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(maxFileSize);
        factory.setMaxRequestSize(maxRequestSize);
        return factory.createMultipartConfig();
    }

}
