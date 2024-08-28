package com.talkka.server.common.util;

import java.util.Optional;

public interface CachedStorage<K, V> {
	Optional<V> get(K key);

	void put(K key, V value);

	void remove(K key);
}
