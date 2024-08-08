package com.talkka.server.config;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.talkka.server.oauth.enums.AuthRole;
import com.talkka.server.oauth.filter.UnregisteredUserFilter;
import com.talkka.server.oauth.service.CustomOAuth2Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
	private final CustomOAuth2Service customOAuth2Service;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/api/auth/login/**").permitAll()
				.requestMatchers("/auth/**", "/login/**").permitAll()
				.requestMatchers(HttpMethod.POST, "/api/auth/register").hasAuthority(AuthRole.UNREGISTERED.getName())
				.anyRequest().authenticated()//.hasAuthority(AuthRole.USER.getName())
			)
			.addFilterAfter(new UnregisteredUserFilter(), BasicAuthenticationFilter.class)
			.oauth2Login(oauth -> oauth
				.userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2Service))
				.successHandler(successHandler())
				.authorizationEndpoint(authorization -> authorization
						.baseUri("/api/auth/login")
					// /* 붙이면 안됨
				)
				.redirectionEndpoint(
					redirection -> redirection
						.baseUri("/api/auth/login/*/code")
					// 반드시 /* 으로 {registrationId}를 받아야 함 스프링 시큐리티의 문제!!
					// https://github.com/spring-projects/spring-security/issues/13251
				)
			)
			.logout(logout -> logout
				.logoutUrl("/api/auth/logout")
				.logoutSuccessUrl("http://localhost:3000")
				.deleteCookies("JSESSIONID")
			)
			.exceptionHandling(exceptionHandling -> exceptionHandling
				.authenticationEntryPoint((request, response, authException) -> {
					log.info(authException.getMessage());
					response.sendError(401, "인증이 필요합니다.");
				})
			);
		return http.build();
	}

	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return new AuthenticationSuccessHandler() {
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
				Authentication authentication) throws IOException, ServletException {
				if (isUnregisteredUser(authentication)) {
					response.sendRedirect("http://localhost:3000/register");
					return;
				}
				response.sendRedirect("http://localhost:3000");
			}

			private boolean isSignUpRequest(HttpServletRequest request) {
				return request.getRequestURI().equals("/auth/signUp");
			}

			private boolean isUnregisteredUser(Authentication authentication) {
				if (authentication == null) {
					return false;
				}
				return hasRole(authentication.getAuthorities(), AuthRole.UNREGISTERED);
			}

			private boolean hasRole(Collection<? extends GrantedAuthority> authorities, AuthRole role) {
				return authorities.stream().anyMatch(authority -> authority.getAuthority().equals(role.getName()));
			}
		};
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();

		config.setAllowCredentials(true);
		config.setAllowedOrigins(List.of("http://localhost:3000"));
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
		config.setAllowedHeaders(List.of("*"));
		config.setExposedHeaders(List.of("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	@Bean
	public HeaderWriterLogoutHandler addLogoutHandler() {
		return new HeaderWriterLogoutHandler(
			new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.COOKIES));
	}
}
