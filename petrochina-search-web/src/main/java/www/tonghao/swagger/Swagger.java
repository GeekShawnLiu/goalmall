package www.tonghao.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
/*
 * 访问地址http://localhost:8088/swagger-ui.html
 */
public class Swagger {
	
	  
	@Bean
    public Docket buildDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
        		//.host("192.168.1.207")
                .apiInfo(buildApiInf())
                .select()
                .apis(RequestHandlerSelectors.basePackage("www.tonghao"))//要扫描的API(Controller)基础包
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo buildApiInf() {
        return new ApiInfoBuilder()
                .title("spe文档")
                //.termsOfServiceUrl("http://127.0.0.1/")
                .contact("spe")
                .version("1.0")
                .build();
    }
}
