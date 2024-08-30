package com.talkka.server.bus.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusLocationRepository extends JpaRepository<BusLocationEntity, Long> {
	List<BusLocationEntity> findByCreatedAtBetween(LocalDateTime startTime, LocalDateTime endTime);
}