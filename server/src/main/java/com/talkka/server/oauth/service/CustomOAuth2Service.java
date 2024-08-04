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
		// Spring Security가 auth provider(naver, kakao, google..)에게 인증 정보를 받아옴
		// 이 시점에선 UNREGISTERED 권한 가지고 있음
		// 만약 DB에 회원정보가 있으면 ROLE_USER 권한 새로 부여
		OAuth2UserInfo oAuth2User = new NaverOAuth2User(super.loadUser(userRequest).getAttributes());
		Map<String, Object> attributes = oAuth2User.getAttributes();
		UserEntity user = userRepository.findByEmail(oAuth2User.getEmail()).orElse(null);
		attributes.put("accessToken", userRequest.getAccessToken().getTokenValue());
		if (user == null) {
			return oAuth2User;
		}
		attributes.put("userId", user.getUserId());
		attributes.put("nickname", user.getNickname());
		return new NaverOAuth2User(attributes,
			List.of(new SimpleGrantedAuthority("ROLE_USER")));
	}
}
