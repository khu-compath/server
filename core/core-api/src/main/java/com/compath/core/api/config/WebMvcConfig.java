package com.compath.core.api.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

	// @Override
	// public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
	// 	resolvers.add(currentAccountArgumentResolver);
	// }

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins(getAllowOrigins())
			.allowedHeaders("Authorization", "Cache-Control", "Content-Type")
			.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
			.allowCredentials(true);
	}

	private String[] getAllowOrigins() {
		return List.of(
			"http://localhost:8080"
		).toArray(String[]::new);
	}
}

