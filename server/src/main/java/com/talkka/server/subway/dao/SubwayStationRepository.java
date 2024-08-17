package com.talkka.server.subway.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubwayStationRepository extends JpaRepository<SubwayStationEntity, Long> {
	List<SubwayStationEntity> findAllByStationNameStartingWithOrderByStationNameAsc(String stationName);

	List<SubwayStationEntity> findAllByOrderByStationNameAsc();

	boolean existsByStationCode(String stationCode);
}
