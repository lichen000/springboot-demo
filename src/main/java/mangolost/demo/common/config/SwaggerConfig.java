package mangolost.demo.common.config;

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
 * Created by chen.li200 on 2019-10-05
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	/**
	 *
	 * @return
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
//				.apis(RequestHandlerSelectors.basePackage("mangolost.demo.controller"))
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}

	/**
	 *
	 * @return
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("springboot-demo后端接口文档")
				.description("springboot-demo后端接口文档")
				.termsOfServiceUrl("http://localhost:9316/api/")
				.contact(new Contact("mangolost", "https://github.com/mangolost", "lichen0_0@163.com"))
				.version("1.0")
				.build();
	}

}
