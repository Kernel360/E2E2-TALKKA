package com.talkka.server.oauth.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public abstract class OAuth2UserInfo implements OAuth2User {

	protected final Map<String, Object> attributes;
	private final Collection<? extends GrantedAuthority> authorities;

	public OAuth2UserInfo(Map<String, Object> attributes, Collection<? extends GrantedAuthority> authorities) {
		this.attributes = attributes;
		this.authorities = authorities;
	}

	// OAuth2 인증까지만 통과하면 UNREGISTERED 권한 부여
	public OAuth2UserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
		this.authorities = List.of(new SimpleGrantedAuthority("UNREGISTERED"));
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getName() {
		return (String)attributes.get("name");
	}

	public String getOAuth2Id() {
		return (String)attributes.get("id");
	}

	public Long getUserId() {
		return (Long)attributes.get("userId");
	}

	public String getEmail() {
		return (String)attributes.get("email");
	}

	public String getAccessToken() {
		return (String)attributes.get("accessToken");
	}

	public String getNickName() {
		return (String)attributes.get("nickname");
	}

	// oauth provider 는 각 제공자 별로 구현
	public abstract String getProvider();
}
