package com.talkka.server.api.datagg.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.talkka.server.admin.dao.PublicApiKeyEntity;
import com.talkka.server.admin.dao.PublicApiKeyRepository;
import com.talkka.server.api.core.config.ApiKeyProvider;
import com.talkka.server.api.core.exception.ApiClientException;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Primary
public class PersistenceApiKeyProvider implements ApiKeyProvider {

	private final PublicApiKeyRepository publicApiKeyRepository;
	@Getter
	private final List<String> keyList = new ArrayList<>();
	@Getter
	private final List<Map<String, Integer>> usageMap = new ArrayList<>();
	private int rollingKeyIndex = 0;
	private final int MAX_USAGE = 950; // 일일 최대 사용량

	@Override
	public String getApiKey(String path) throws ApiClientException {
		for (int i = 0; i < keyList.size(); i++) {
			rollingKeyIndex = (rollingKeyIndex + 1) % keyList.size();
			// 호출한 적이 없거나 호출횟수가 MAX_USAGE 보다 작으면 api key 제공
			if (!usageMap.get(rollingKeyIndex).containsKey(path)
				|| usageMap.get(rollingKeyIndex).get(path) < MAX_USAGE) {
				updateUsage(rollingKeyIndex, path);
				return keyList.get(rollingKeyIndex);
			}
		}
		throw new ApiClientException("사용 가능한 키가 없습니다.");
	}

	// 매일 자정에 키 사용량 리셋
	@PostConstruct
	@Scheduled(cron = "0 0 0 * * *")
	public void init() {
		rollingKeyIndex = 0;
		keyList.clear();
		usageMap.clear();
		var keys = publicApiKeyRepository.findAll();
		for (var key : keys) {
			keyList.add(key.getSecret());
			usageMap.add(new TreeMap<>());
		}
	}

	public void addKey(PublicApiKeyEntity key) {
		keyList.add(key.getSecret());
		usageMap.add(new TreeMap<>());
	}

	public void deleteKey(String secret) {
		int idx = keyList.indexOf(secret);
		keyList.remove(idx);
		usageMap.remove(idx);
	}

	private void updateUsage(int idx, String path) {
		var map = usageMap.get(idx);
		if (!map.containsKey(path)) {
			map.put(path, 1);
		} else {
			map.put(path, map.get(path) + 1);
			if (map.get(path) > MAX_USAGE) {
				map.remove(path);
			}
		}
	}
}
