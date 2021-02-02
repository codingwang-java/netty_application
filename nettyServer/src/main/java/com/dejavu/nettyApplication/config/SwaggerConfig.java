package com.dejavu.nettyApplication.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.security.cert.X509Certificate;
/**
 * description：
 * 
 * @ClassName SwaggerConfig
 * @Description swagger配置
 * @Author DEJAVU
 * @Date 2021/2/1 23:02
 * @Version 1.0
 */
@Configurable
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket creatApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())   // 指定构建api文档的详细信息的方法，下面会有实现：apiInfo()
                .select()
                // 指定要生成api接口的包路径，这里把controller作为包路径，生成controller中的所有接口
                .apis(RequestHandlerSelectors.basePackage("com.dejavu.nettyApplication.webController"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot集成Swagger2接口测试")
                .description("生成的接口如下")
                .version("1.0")
                .build();
    }
}
