package com.talkka.server.oauth;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.talkka.server.oauth.domain.AuthUserDto;
import com.talkka.server.oauth.domain.CustomUserPrincipal;
import com.talkka.server.user.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final UserService userService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		AuthUserDto userDto = ((CustomUserPrincipal)authentication.getPrincipal()).getUser();
		if (!userDto.getIsRegistered()) {
			getRedirectStrategy().sendRedirect(request, response, "/auth/signUp");
		}
	}
}
