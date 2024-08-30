package com.talkka.server.oauth.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import com.talkka.server.oauth.domain.NaverOAuth2User;
import com.talkka.server.oauth.enums.AuthRole;
import com.talkka.server.user.dao.UserEntity;
import com.talkka.server.user.dao.UserRepository;
import com.talkka.server.user.vo.Email;
import com.talkka.server.user.vo.Nickname;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DevLoginService {

	private final UserRepository userRepository;

	public Authentication getAuthentication(AuthRole authRole) {
		UserEntity entity = getUserEntity(authRole);

		Map<String, Object> attributes = new HashMap<>();
		attributes.put("id", "test");
		attributes.put("username", "테스트 유저");
		attributes.put("userId", entity.getId());
		attributes.put("name", entity.getName());
		attributes.put("email", entity.getEmail());
		attributes.put("accessToken", entity.getAccessToken());
		attributes.put("nickname", entity.getNickname());

		return getoAuth2AuthenticationToken(attributes, entity, authRole);
	}

	private UserEntity getUserEntity(AuthRole authRole) throws IllegalArgumentException {
		Email email = new Email(authRole.getName() + "@test.com");

		return userRepository.findByEmail(email).orElseGet(
			() -> createTestUserEntity(authRole));
	}

	private UserEntity createTestUserEntity(AuthRole authRole) {
		String authRoleName = authRole.getName();
		Email email = new Email(authRoleName + "@test.com");

		return userRepository.save(UserEntity.builder()
			.name("테스트" + authRole)
			.email(email)
			.nickname(new Nickname(authRoleName))
			.oauthProvider("TEST")
			.accessToken("token")
			.authRole(authRole)
			.build());
	}

	private static OAuth2AuthenticationToken getoAuth2AuthenticationToken(
		Map<String, Object> attributes, UserEntity user, AuthRole role
	) {

		OAuth2AuthenticationToken oAuth2AuthenticationToken;
		oAuth2AuthenticationToken = new OAuth2AuthenticationToken(
			new NaverOAuth2User(attributes, List.of(role::getName)),
			List.of(role::getName),
			user.getOauthProvider()
		);
		return oAuth2AuthenticationToken;
	}
}
