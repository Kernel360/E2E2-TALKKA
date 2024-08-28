package com.talkka.server.bus.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRouteStationRepository extends JpaRepository<BusRouteStationEntity, Long> {
	List<BusRouteStationEntity> findAllByRouteId(Long routeId);

	List<BusRouteStationEntity> findAllByStationId(Long stationId);

	List<BusRouteStationEntity> findAllByRouteIdAndStationId(Long routeId, Long stationId);

	// 노선의 이전/다음 정거장 존재 유무를 확인하기 위함
	BusRouteStationEntity findByRouteAndStationSeq(BusRouteEntity route, Short stationSeq);

	int countAllByRoute(BusRouteEntity route);
}
