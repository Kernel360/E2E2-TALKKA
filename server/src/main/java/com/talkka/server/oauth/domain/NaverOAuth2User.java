package com.talkka.server.oauth.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@SuppressWarnings("unchecked")
public class NaverOAuth2User extends OAuth2UserInfo {

	public NaverOAuth2User(Map<String, Object> attributes) {
		super((Map<String, Object>)attributes.get("response"),
			List.of(new SimpleGrantedAuthority("ROLE_UNREGISTERED")));
	}

	public NaverOAuth2User(Map<String, Object> attributes, Collection<? extends GrantedAuthority> authorities) {
		super((Map<String, Object>)attributes, authorities);
	}

	@Override
	public String getOAuth2Id() {
		return (String)attributes.get("id");
	}

	@Override
	public String getEmail() {
		return (String)attributes.get("email");
	}

	@Override
	public String getName() {
		return (String)attributes.get("name");
	}

	public String getProvider() {
		return "NAVER";
	}
}
