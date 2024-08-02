package com.talkka.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.talkka.server.oauth.OAuth2LoginFailureHandler;
import com.talkka.server.oauth.service.CustomOAuth2Service;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomOAuth2Service customOAuth2Service;
	private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.cors(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/auth/**", "/login/**").permitAll()
				.anyRequest().authenticated()
			)
			.addFilterBefore(new CustomRedirectFilter(), BasicAuthenticationFilter.class)
			.oauth2Login(oauth -> oauth
				.userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2Service))
				.defaultSuccessUrl("/")
			)
			.exceptionHandling(exceptionHandling -> exceptionHandling
				.authenticationEntryPoint((request, response, authException) -> {
					response.sendRedirect("/auth/login");
				})
			);
		return http.build();
	}
}
