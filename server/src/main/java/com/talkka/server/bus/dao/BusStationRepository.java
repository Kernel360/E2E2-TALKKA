package com.talkka.server.bus.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusStationRepository extends JpaRepository<BusStationEntity, Long> {
	Optional<BusStationEntity> findByApiStationId(String apiStationId);

	List<BusStationEntity> findByStationNameLikeOrderByStationNameAsc(String stationName);

	boolean existsByApiStationId(String apiStationId);
}
