package com.talkka.server.admin.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.talkka.server.admin.dao.CollectBusRouteEntity;
import com.talkka.server.admin.dao.CollectBusRouteRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Primary
public class PersistenceCollectedRouteProvider implements CollectedRouteProvider {
	private final List<String> targetIdList = new ArrayList<>();
	private final CollectBusRouteRepository collectBusRouteRepository;

	@PostConstruct
	public void init() {
		targetIdList.addAll(
			collectBusRouteRepository.findAll().stream()
				.map(CollectBusRouteEntity::getApiRouteId)
				.toList()
		);
	}

	@Override
	public List<String> getTargetIdList() {
		return targetIdList;
	}
}
