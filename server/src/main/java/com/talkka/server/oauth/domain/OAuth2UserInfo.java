package com.talkka.server.oauth.domain;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public abstract class OAuth2UserInfo implements OAuth2User {

	protected final Map<String, Object> attributes;
	private final Collection<? extends GrantedAuthority> authorities;

	public OAuth2UserInfo(Map<String, Object> attributes, Collection<? extends GrantedAuthority> authorities) {
		this.attributes = attributes;
		this.authorities = authorities;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public abstract String getOAuth2Id();

	public abstract String getEmail();

	public abstract String getProvider();

}
