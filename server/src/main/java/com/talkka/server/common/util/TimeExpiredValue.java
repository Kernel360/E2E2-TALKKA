package com.talkka.server.common.util;

public record TimeExpiredValue<T>(long expiredTime, T value) {
	public static <T> TimeExpiredValue<T> create(T value, long expiredTime) {
		return new TimeExpiredValue<>(expiredTime, value);
	}

	public static <T> TimeExpiredValue<T> create(T value) {
		return new TimeExpiredValue<>(System.currentTimeMillis(), value);
	}

	public boolean isExpired() {
		return System.currentTimeMillis() > expiredTime;
	}
}
