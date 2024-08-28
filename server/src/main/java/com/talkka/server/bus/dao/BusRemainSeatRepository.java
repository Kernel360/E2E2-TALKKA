package com.talkka.server.bus.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talkka.server.bus.enums.PlateType;

public interface BusRemainSeatRepository extends JpaRepository<BusRemainSeatEntity, Long> {
	List<BusRemainSeatEntity> findByRouteIdAndStationIdAndEpochDayAndTimeBetween(Long routeId, Long stationId,
		Long epochDay, Integer startTime, Integer endTime);

	List<BusRemainSeatEntity> findByRouteInfoAndPlateTypeAndStationSeqBetweenOrderByStationSeq(
		BusPlateStatisticEntity routeInfo,
		PlateType plateType, Integer startSeq, Integer endSeq);

	List<BusRemainSeatEntity> findByRouteInfoAndStationSeqBetweenOrderByStationSeq(BusPlateStatisticEntity routeInfo,
		Integer startSeq, Integer endSeq);
}
