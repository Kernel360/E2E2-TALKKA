package com.talkka.server.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talkka.server.admin.dao.PublicApiKeyEntity;
import com.talkka.server.admin.dao.PublicApiKeyRepository;
import com.talkka.server.admin.dto.PublicApiKeyRespDto;
import com.talkka.server.admin.exception.PublicApiKeyAlreadyExistsException;
import com.talkka.server.admin.exception.PublicApiKeyNotFoundException;
import com.talkka.server.api.datagg.config.PersistenceApiKeyProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublicApiKeyService {
	private final PublicApiKeyRepository publicApiKeyRepository;
	private final PersistenceApiKeyProvider persistenceApiKeyProvider;

	@Transactional
	public PublicApiKeyRespDto createKey(String secret) throws PublicApiKeyAlreadyExistsException {
		if (publicApiKeyRepository.existsBySecret(secret)) {
			throw new PublicApiKeyAlreadyExistsException();
		}
		var key = publicApiKeyRepository.save(
			PublicApiKeyEntity.builder()
				.secret(secret)
				.build()
		);
		persistenceApiKeyProvider.addKey(key);
		// 처음 생성시에는 빈 사용량맵 반환
		return PublicApiKeyRespDto.of(key, new TreeMap<>());
	}

	@Transactional
	public void deleteKey(String secret) throws PublicApiKeyNotFoundException {
		if (!publicApiKeyRepository.existsBySecret(secret)) {
			throw new PublicApiKeyNotFoundException();
		}
		publicApiKeyRepository.deleteBySecret(secret);
		persistenceApiKeyProvider.deleteKey(secret);
	}

	public List<PublicApiKeyRespDto> getKeyList() {
		List<PublicApiKeyRespDto> result = new ArrayList<>();
		var keyEntityList = publicApiKeyRepository.findAll();
		var secretList = persistenceApiKeyProvider.getKeyList();
		var usageMapList = persistenceApiKeyProvider.getUsageMap();
		for (int i = 0; i < secretList.size(); i++) {
			for (var keyEntity : keyEntityList) {
				if (keyEntity.getSecret().equals(secretList.get(i))) {
					result.add(PublicApiKeyRespDto.of(keyEntity, usageMapList.get(i)));
					break;
				}
			}
		}
		return result;
	}
}
