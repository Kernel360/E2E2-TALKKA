package com.talkka.server.admin.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.talkka.server.admin.dao.PublicApiKeyEntity;
import com.talkka.server.admin.dao.PublicApiKeyRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Primary
public class PersistenceApiKeyProvider implements ApiKeyProvider {

	private final PublicApiKeyRepository publicApiKeyRepository;
	private final List<PublicApiKeyEntity> keys = new ArrayList<>();
	private int rollingKeyIndex = 0;
	private final int MAX_USAGE = 950; // 일일 최대 사용량

	@Override
	public String getApiKey() {
		rollingKeyIndex = (rollingKeyIndex + 1) % keys.size();
		updateUsage(keys.get(rollingKeyIndex));
		return keys.get(rollingKeyIndex).getSecret();
	}

	// 매일 자정에 키 사용량 리셋
	@PostConstruct
	@Scheduled(cron = "0 0 0 * * *")
	public void init() {
		rollingKeyIndex = 0;
		keys.clear();
		keys.addAll(publicApiKeyRepository.findAll());
		// 사용량 초기화
		keys.forEach(PublicApiKeyEntity::reset);
		publicApiKeyRepository.saveAll(keys);
	}

	// 20분마다 사용량 DB에 반영
	@Scheduled(cron = "0 */20 * * * *")
	private void persist() {
		System.out.println("persist key!!");
		publicApiKeyRepository.saveAll(keys);
	}

	private void updateUsage(PublicApiKeyEntity key) {
		key.use();
		// 일일 사용량이 초과되면 리스트에서 삭제 후 DB에 반영
		if (key.getKeyUsage() >= MAX_USAGE) {
			keys.remove(key);
			publicApiKeyRepository.save(key);
		}
	}
}
