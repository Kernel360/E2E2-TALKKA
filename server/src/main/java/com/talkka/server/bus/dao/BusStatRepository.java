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

	@Query("SELECT b FROM bus_stat b WHERE b.apiRouteId = :apiRouteId AND b.apiStationId = :apiStationId AND b.beforeTime BETWEEN :startTime AND :endTime")
	List<BusStatEntity> findByApiRouteIdAndApiStationIdAndBeforeTimeBetween(
		@Param("apiRouteId") String apiRouteId,
		@Param("apiStationId") String apiStationId,
		@Param("startTime") LocalDateTime startTime,
		@Param("endTime") LocalDateTime endTime
	);
}
