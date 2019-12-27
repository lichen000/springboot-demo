package com.mangolost.demo.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * CORS跨域配置
 * Created by mangolost on 2017-10-20
 */
@Configuration
public class MyCorsConfig {

	/**
	 *
	 * @return
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**")
						.allowedOrigins("*")
						.allowedMethods("PUT", "GET", "POST", "HEAD", "OPTIONS", "DELETE")
						.allowCredentials(false)
						.maxAge(3600);
			}
		};
	}

	@Configuration
	public static class WebSocketConfig {

		@Bean
		public ServerEndpointExporter serverEndpointExporter() {
			return new ServerEndpointExporter();
		}
	}
}
