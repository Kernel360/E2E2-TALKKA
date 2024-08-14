package com.talkka.server.review.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talkka.server.common.enums.TimeSlot;

@Repository
public interface BusReviewRepository extends JpaRepository<BusReviewEntity, Long> {

	List<BusReviewEntity> findAllByWriterIdAndRouteIdAndStationIdAndTimeSlotOrderByUpdatedAtDesc(
		Long userId,
		Long routeId,
		Long busRouteStationId,
		TimeSlot timeSlot);

	List<BusReviewEntity> findAllByRouteIdOrderByCreatedAtDesc(Long routeId);

	List<BusReviewEntity> findAllByRouteIdAndStationIdOrderByCreatedAtDesc(Long routeId, Long stationId);

	List<BusReviewEntity> findAllByRouteIdAndStationIdAndTimeSlotOrderByCreatedAtDesc(
		Long routeId,
		Long stationId,
		TimeSlot timeSlot);
}
