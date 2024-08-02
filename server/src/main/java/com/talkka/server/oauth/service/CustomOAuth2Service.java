package com.talkka.server.oauth.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.talkka.server.oauth.domain.NaverOAuth2User;
import com.talkka.server.user.dao.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2Service extends DefaultOAuth2UserService {

	private final UserRepository userRepository;

	@SuppressWarnings({"checkstyle:RegexpSingleline", "checkstyle:WhitespaceAfter"})
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		NaverOAuth2User oAuth2User = new NaverOAuth2User(super.loadUser(userRequest).getAttributes());
		if (userRepository.existsByEmail(oAuth2User.getEmail())) {
			Map<String, Object> attributes = oAuth2User.getAttributes();
			return new NaverOAuth2User(attributes,
				List.of(new SimpleGrantedAuthority("ROLE_USER")));
		}
		return oAuth2User;
	}
}
