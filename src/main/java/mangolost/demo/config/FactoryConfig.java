package mangolost.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * Created by mangolost on 2017-04-28
 */
@Configuration
public class FactoryConfig {

	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}

}
