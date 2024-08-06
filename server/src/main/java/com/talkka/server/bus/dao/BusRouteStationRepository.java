package com.talkka.server.bus.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRouteStationRepository extends JpaRepository<BusRouteStationEntity, Long> {
	List<BusRouteStationEntity> findByRouteId(Long routeId);

	List<BusRouteStationEntity> findByStationId(Long stationId);
}
