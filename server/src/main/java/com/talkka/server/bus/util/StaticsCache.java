package com.talkka.server.bus.util;

import org.springframework.stereotype.Component;

import com.talkka.server.bus.dto.BusStaticsDto;
import com.talkka.server.common.util.MemoryCachedStorage;

@Component
public class StaticsCache extends MemoryCachedStorage<StaticsCacheKey, BusStaticsDto> {
	public StaticsCache() {
		// 1일
		super(60 * 60 * 24 * 1);
	}
}
