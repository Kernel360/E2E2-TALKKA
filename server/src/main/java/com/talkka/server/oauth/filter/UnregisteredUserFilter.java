package com.talkka.server.oauth.filter;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.talkka.server.oauth.enums.AuthRole;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UnregisteredUserFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		if (isUnregisteredUser(authentication) && !isSignUpRequest(httpRequest)) {
			httpResponse.sendRedirect("/register");
			return;
		}
		chain.doFilter(request, response);
	}

	private boolean isSignUpRequest(HttpServletRequest request) {
		return request.getRequestURI().equals("/api/auth/register") && request.getMethod().equals("POST");
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
}
