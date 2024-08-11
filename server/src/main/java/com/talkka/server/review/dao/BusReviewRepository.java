package com.talkka.server.review.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talkka.server.common.enums.TimeSlot;

@Repository
public interface BusReviewRepository extends JpaRepository<BusReviewEntity, Long> {
	List<BusReviewEntity> findAllByWriterIdAndRouteIdAndStationIdAndTimeSlot(
		Long userId,
		Long routeId,
		Long busRouteStationId,
		TimeSlot timeSlot);
}
