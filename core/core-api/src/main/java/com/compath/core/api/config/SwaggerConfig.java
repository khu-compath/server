package com.compath.core.api.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI api() {
		Server server = new Server().url("/");
		SecurityScheme securityScheme = new SecurityScheme()
			.type(SecurityScheme.Type.HTTP).scheme("Bearer").bearerFormat("JWT")
			.in(SecurityScheme.In.HEADER).name("Authorization");
		SecurityRequirement securityRequirement = new SecurityRequirement().addList("Bearer");

		return new OpenAPI()
			.info(getSwaggerInfo())
			.components(new Components().addSecuritySchemes("Bearer", securityScheme))
			.security(Collections.singletonList(securityRequirement))
			.addServersItem(server);
	}

	private Info getSwaggerInfo() {
		License license = new License();
		license.setName("MIT License");
		license.setUrl("https://opensource.org/licenses/MIT");

		return new Info()
			.title("Compath API Document")
			.description("Compath API 문서 입니다.")
			.version("v0.0.1")
			.license(license);
	}
}
