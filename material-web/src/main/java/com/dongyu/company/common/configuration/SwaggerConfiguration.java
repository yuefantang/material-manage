package com.dongyu.company.common.configuration;

import com.dongyu.company.common.vo.ExceptionVo;
import com.dongyu.company.common.vo.FieldExceptionVo;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * swagger相关配置
 *
 * @author TYF
 * @date 2018/11/8
 * @since 1.0.0
 */
@Configuration
@EnableSwagger2
//@ConditionalOnProperty(name = "enableswagger", havingValue = "true")
public class SwaggerConfiguration implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {
    @Autowired
    private TypeResolver typeResolver;
    @Value("${server.context-path}")
    private String path;

    @Bean
    public Docket createRestApi() {
        ModelRef errorModel = new ModelRef("ExceptionVo");
        List<ResponseMessage> responseMessages = Arrays.asList(
                new ResponseMessageBuilder().code(409).message("Conflict")
                        .responseModel(new ModelRef("FieldExceptionVo")).build(),
                new ResponseMessageBuilder().code(403).message("Forbiddon").responseModel(null).build(),
                new ResponseMessageBuilder().code(404).message("Not Found").responseModel(null).build(),
                new ResponseMessageBuilder().code(500).message("Intenal Error").responseModel(null).build()
        );
        List<Parameter> headerParameter = new ArrayList<>();
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.dongyu.company"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(headerParameter).apiInfo(apiInfo()).useDefaultResponseMessages(false)
                .additionalModels(typeResolver.resolve(FieldExceptionVo.class), typeResolver.resolve(ExceptionVo.class))
                .globalResponseMessage(RequestMethod.POST, responseMessages)
                .globalResponseMessage(RequestMethod.GET, responseMessages)
                .globalResponseMessage(RequestMethod.PUT, responseMessages)
                .globalResponseMessage(RequestMethod.DELETE, responseMessages);
    }

    @Override
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
        int port = event.getEmbeddedServletContainer().getPort();
        String hostAddress = "localhost";
        try {
            String address = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        // String contextPath = event.getApplicationContext().getServletContext().getContextPath();
        System.err.println("swagger访问地址：http://" + hostAddress + ":" + port + path + "swagger-ui.html");
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("程序猿战斗小组", "", "");
        return new ApiInfoBuilder().title("Material Project DOC").contact(contact).description("东宇电子").build();
    }
}
