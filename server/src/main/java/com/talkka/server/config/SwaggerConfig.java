package com.talkka.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration    // 스프링 실행시 설정파일 읽어드리기 위한 어노테이션
public class SwaggerConfig {

	@Bean
	public OpenAPI openApi() {
		return new OpenAPI()
			.components(components())
			.addSecurityItem(securityRequirement())
			.info(apiInfo());
	}

	/**
	 * Swagger Security 설정 추가
	 * - AuthRole 에 해당하는 Authentication 을 OpenAPI 에 추가
	 */
	private Components components() {
		return new Components()
			.addSecuritySchemes("unregistered", securityScheme())
			.addSecuritySchemes("admin", securityScheme())
			.addSecuritySchemes("user", securityScheme());
	}

	/**
	 * Swagger Security 설정 추가
	 *  Authentication 방식을 OpenAPI 에 추가
	 */
	private SecurityScheme securityScheme() {
		return new SecurityScheme()
			.type(SecurityScheme.Type.APIKEY)
			.name("JSESSIONID")
			.in(SecurityScheme.In.COOKIE);
	}

	/**
	 * Swagger Security 설정 추가
	 * - AuthRole 에 해당하는 Authentication 을 OpenAPI 에 추가
	 */
	private SecurityRequirement securityRequirement() {
		return new SecurityRequirement()
			.addList("user")
			.addList("admin");
	}

	/**
	 * Swagger API 정보 설정
	 */
	private Info apiInfo() {
		return new Info()
			.title("탈까 API")
			.description("탈까 서비스 API 명세서")
			.version("1.0.0");
	}
}
