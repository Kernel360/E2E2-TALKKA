package com.talkka.server.bus.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.talkka.server.api.datagg.dto.BusLocationBodyDto;
import com.talkka.server.bus.dao.BusLocationEntity;
import com.talkka.server.bus.dao.BusLocationRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusLocationService {
	private final BusLocationRepository busLocationRepository;

	@Transactional
	public void saveBusLocations(List<BusLocationBodyDto> responseList, Integer apiCallNo, LocalDateTime createdAt) {
		List<BusLocationEntity> entityList = responseList.stream()
			.map(dto -> dto.toEntity(apiCallNo, createdAt))
			.toList();
		busLocationRepository.saveAll(entityList);
	}
}
