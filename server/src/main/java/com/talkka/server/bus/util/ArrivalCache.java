package com.talkka.server.bus.util;

import org.springframework.stereotype.Component;

import com.talkka.server.bus.dto.BusLiveArrivalRespDto;
import com.talkka.server.common.util.MemoryCachedStorage;

@Component
public class ArrivalCache extends MemoryCachedStorage<Long, BusLiveArrivalRespDto> {
	ArrivalCache() {
		super(60); // 60초 주기로 캐싱 무효화
	}
}
