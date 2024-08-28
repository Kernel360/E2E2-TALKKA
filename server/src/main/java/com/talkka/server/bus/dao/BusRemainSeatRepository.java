package com.talkka.server.bus.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRemainSeatRepository extends JpaRepository<BusRemainSeatEntity, Long> {
	List<BusRemainSeatEntity> findByRouteIdAndStationIdAndEpochDayAndTimeBetween(Long routeId, Long stationId,
		Long epochDay, Integer startTime, Integer endTime);

	List<BusRemainSeatEntity> findByPlateStatisticAndStationSeqBetweenOrderByStationSeq(
		BusPlateStatisticEntity routeInfo,
		Integer startSeq, Integer endSeq);
}
