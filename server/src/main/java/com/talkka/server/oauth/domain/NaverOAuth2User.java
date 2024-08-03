package com.talkka.server.oauth.domain;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;

public class NaverOAuth2User extends OAuth2UserInfo {

	public NaverOAuth2User(Map<String, Object> attributes) {
		super((Map<String, Object>)attributes.get("response"));
	}

	public NaverOAuth2User(Map<String, Object> attributes, Collection<? extends GrantedAuthority> authorities) {
		super((Map<String, Object>)attributes, authorities);
	}

	@Override
	public String getProvider() {
		return "NAVER";
	}

}
