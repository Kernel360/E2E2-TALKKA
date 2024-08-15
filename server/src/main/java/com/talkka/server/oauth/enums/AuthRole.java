package com.talkka.server.oauth.enums;

public enum AuthRole {
	UNREGISTERED("UNREGISTERED"),
	USER("USER"),
	ADMIN("ADMIN");

	private final String name;

	AuthRole(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
