package com.talkka.server.admin.dto;

import java.time.LocalDateTime;
import java.util.Map;

import com.talkka.server.admin.dao.PublicApiKeyEntity;

public record PublicApiKeyRespDto(
	Long id,
	String secret,
	Map<String, Integer> keyUsage,
	LocalDateTime createdAt
) {
	public static PublicApiKeyRespDto of(PublicApiKeyEntity key, Map<String, Integer> usageMap) {
		return new PublicApiKeyRespDto(
			key.getId(),
			key.getSecret(),
			usageMap,
			key.getCreatedAt()
		);
	}
}
