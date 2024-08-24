package com.talkka.server.bus.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BusStatRepository extends JpaRepository<BusStatEntity, Long> {
	List<BusStatEntity> findByBeforeTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

	List<BusStatEntity> findByRouteIdAndStationIdAndBeforeTimeBetween(
		Long routeId,
		Long stationId,
		LocalDateTime startTime,
		LocalDateTime endTime
	);

	@Query("SELECT b FROM bus_stat b WHERE b.route.id = :routeId AND b.station.id = :stationId AND b.dayOfWeek = :dayOfWeek AND (b.time >= :startTime OR b.time <= :endTime)")
	List<BusStatEntity> findByRouteIdAndStationIdAndDayOfWeekBetweenNow(
		@Param("routeId") Long routeId,
		@Param("stationId") Long stationId,
		@Param("dayOfWeek") Integer dayOfWeek,
		@Param("startTime") int startTime,
		@Param("endTime") int endTime
	);

}
