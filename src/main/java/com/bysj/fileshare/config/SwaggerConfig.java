package com.bysj.fileshare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ProjectName: fileshare
 * @Package: com.bysj.fileshare.config
 * @ClassName: SwaggerConfig
 * @Description: java类作用描述
 * @Author: 徐大伟
 * @CreateDate: 2020/5/22 11:43 下午
 * @UpdateUser:
 * @UpdateDate: 2020/5/22 11:43 下午
 * @UpdateRemark:
 * @Version: 1.0
 * @Copyright: 上海昱泓教育科技有限公司
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 自行修改为自己的包路径
                .apis(RequestHandlerSelectors.basePackage("com.bysj.fileshare.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Springboot使用swagger在线文档")
                .description("swagger UI接入教程")
                //服务条款网
               // .termsOfServiceUrl("")
                //版本号
                .version("1.0")
                //.contact(new Contact("我的毕业设计", "https://www.cnblogs.com/saoyou/", "2317818786@qq.com"))
                .build();
    }

}
