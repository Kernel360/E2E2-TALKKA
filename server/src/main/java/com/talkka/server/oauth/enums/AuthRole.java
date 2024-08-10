package com.talkka.server.oauth.enums;

public enum AuthRole {
	UNREGISTERED("UNREGISTERED"),
	USER("USER");

	private final String name;

	AuthRole(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
