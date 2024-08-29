package com.talkka.server.admin.dto;

import java.time.LocalDateTime;

import com.talkka.server.admin.dao.PublicApiKeyEntity;

public record PublicApiKeyRespDto(
	Long id,
	String secret,
	Integer keyUsage,
	LocalDateTime createdAt
) {
	public static PublicApiKeyRespDto of(PublicApiKeyEntity key) {
		return new PublicApiKeyRespDto(
			key.getId(),
			key.getSecret(),
			key.getKeyUsage(),
			key.getCreatedAt()
		);
	}
}
