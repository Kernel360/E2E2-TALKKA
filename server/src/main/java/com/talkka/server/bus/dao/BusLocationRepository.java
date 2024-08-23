package com.talkka.server.bus.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BusLocationRepository extends JpaRepository<BusLocationEntity, Long> {

	@Query(value = "select distinct l.apiCallNo from bus_location l")
	List<Integer> getDistinctApiCallNoList();

	@Query(value = "select count(*) from bus_location  l")
	Integer getRowNum();

	List<BusLocationEntity> findByApiCallNo(Integer apiCallNo);

	List<BusLocationEntity> findByCreatedAtBetween(LocalDateTime startTime, LocalDateTime endTime);

}