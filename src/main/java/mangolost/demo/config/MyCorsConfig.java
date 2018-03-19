package mangolost.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * CORS跨域配置
 * Created by mangolost on 2017-10-20
 */
@Configuration
public class MyCorsConfig {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**")
						.allowedOrigins("*")
						.allowedMethods("PUT", "GET", "HEAD", "OPTIONS")
						.allowCredentials(true)
						.maxAge(3600);
			}
		};
	}
}
