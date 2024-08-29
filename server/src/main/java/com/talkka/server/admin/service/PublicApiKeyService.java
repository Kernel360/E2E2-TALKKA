package com.talkka.server.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talkka.server.admin.dao.PublicApiKeyEntity;
import com.talkka.server.admin.dao.PublicApiKeyRepository;
import com.talkka.server.admin.dto.PublicApiKeyRespDto;
import com.talkka.server.admin.exception.PublicApiKeyAlreadyExistsException;
import com.talkka.server.admin.exception.PublicApiKeyNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublicApiKeyService {
	private final PublicApiKeyRepository publicApiKeyRepository;

	@Transactional
	public PublicApiKeyRespDto createKey(String secret) throws PublicApiKeyAlreadyExistsException {
		if (publicApiKeyRepository.existsBySecret(secret)) {
			throw new PublicApiKeyAlreadyExistsException();
		}
		var key = publicApiKeyRepository.save(
			PublicApiKeyEntity.builder()
				.secret(secret)
				.keyUsage(0)
				.build()
		);
		return PublicApiKeyRespDto.of(key);
	}

	@Transactional
	public void deleteKey(String secret) throws PublicApiKeyNotFoundException {
		if (!publicApiKeyRepository.existsBySecret(secret)) {
			throw new PublicApiKeyNotFoundException();
		}
		publicApiKeyRepository.deleteBySecret(secret);
	}

	public List<PublicApiKeyRespDto> getKeyList() {
		return publicApiKeyRepository.findAll().stream()
			.map(PublicApiKeyRespDto::of)
			.toList();
	}
}
