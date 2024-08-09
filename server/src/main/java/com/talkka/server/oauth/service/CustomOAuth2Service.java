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
import com.talkka.server.oauth.domain.OAuth2UserInfo;
import com.talkka.server.oauth.enums.AuthRole;
import com.talkka.server.user.dao.UserEntity;
import com.talkka.server.user.dao.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2Service extends DefaultOAuth2UserService {
	private final UserRepository userRepository;

	// REFACTOR 반드시 필요함!
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserInfo oAuth2User = new NaverOAuth2User(super.loadUser(userRequest).getAttributes());
		Map<String, Object> attributes = oAuth2User.getAttributes();
		UserEntity user = userRepository.findByEmail(oAuth2User.getEmail()).orElse(null);
		attributes.put("accessToken", userRequest.getAccessToken().getTokenValue());
		if (user == null) {
			return oAuth2User;
		}
		attributes.put("userId", user.getId());
		attributes.put("nickname", user.getNickname());
		return new NaverOAuth2User(attributes,
			List.of(new SimpleGrantedAuthority(AuthRole.USER.getName())));
	}
}
