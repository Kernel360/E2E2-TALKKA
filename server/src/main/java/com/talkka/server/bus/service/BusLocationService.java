package com.talkka.server.bus.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talkka.server.api.datagg.dto.BusLocationBodyDto;
import com.talkka.server.bus.dao.BusLocationEntity;
import com.talkka.server.bus.dao.BusLocationRepository;

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

	public List<BusLocationEntity> findAll() {
		return busLocationRepository.findAll();
	}

	public List<BusLocationEntity> findByPeriod(LocalDateTime start, LocalDateTime end) {
		return busLocationRepository.findByCreatedAtBetween(start, end);
	}
}
