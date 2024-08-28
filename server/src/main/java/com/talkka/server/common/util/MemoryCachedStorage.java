package com.talkka.server.common.util;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MemoryCachedStorage
 * Redis 사용 이전에 메모리에 캐시를 저장하는 클래스
 * 사용량이 증가할 경우 put 에서 removeExpired 를 호출하여 메모리를 정리에 시간이 크게 소요될 수 있음
 * (임시방편으로 사용하고 있으며 추후 Redis 로 대체할 예정)
 * @param <K>
 * @param <V>
 */
public abstract class MemoryCachedStorage<K, V> implements CachedStorage<K, V> {
	private final Map<K, TimeExpiredValue<V>> cache = new ConcurrentHashMap<>();
	private final Integer expireTimeSeconds;

	public MemoryCachedStorage(Integer expireTimeSeconds) {
		this.expireTimeSeconds = expireTimeSeconds;
	}

	@Override
	public void put(K key, V value) {
		TimeExpiredValue<V> timeExpiredValue = TimeExpiredValue.create(value,
			System.currentTimeMillis() + expireTimeSeconds * 1000L);
		cache.put(key, timeExpiredValue);
		removeExpired();
	}

	@Override
	public Optional<V> get(K key) {
		TimeExpiredValue<V> timeExpiredValue = cache.get(key);

		if (timeExpiredValue == null) {
			return Optional.empty();
		}
		if (timeExpiredValue.isExpired()) {
			cache.remove(key);
			return Optional.empty();
		}
		return Optional.of(timeExpiredValue.value());
	}

	@Override
	public void remove(K key) {
		cache.remove(key);
	}

	private void removeExpired() {
		cache.entrySet().removeIf(entry -> entry.getValue().isExpired());
	}
}
