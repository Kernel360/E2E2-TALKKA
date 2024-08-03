package com.talkka.server.oauth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthUserDto {
	private String name;
	private String email;
	private String accessToken;
	private String provider;
	private Boolean isRegistered;
}
