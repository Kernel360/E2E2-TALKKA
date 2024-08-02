package com.talkka.server.oauth.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.talkka.server.oauth.domain.AuthUserDto;
import com.talkka.server.oauth.domain.CustomUserPrincipal;
import com.talkka.server.user.dao.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2Service extends DefaultOAuth2UserService {

	private final UserRepository userRepository;

	@SuppressWarnings("checkstyle:RegexpSingleline")
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		Map<String, Object> tmpMap = oAuth2User.getAttribute("response");
		assert tmpMap != null;
		String email = (String)tmpMap.get("email");
		String name = (String)tmpMap.get("name");
		String accessToken = userRequest.getAccessToken().getTokenValue();
		String provider = userRequest.getClientRegistration().getClientName();
		boolean isRegistered = userRepository.existsByEmail(email);

		AuthUserDto userDto = AuthUserDto.builder()
			.name(name)
			.email(email)
			.accessToken(accessToken)
			.provider(provider)
			.isRegistered(isRegistered)
			.build();

		return new CustomUserPrincipal(userDto, new HashMap<>());
	}
}
