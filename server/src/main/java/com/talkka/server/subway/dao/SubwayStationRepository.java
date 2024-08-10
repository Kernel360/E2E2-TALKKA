package com.talkka.server.subway.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubwayStationRepository extends JpaRepository<SubwayStationEntity, Long> {
}
