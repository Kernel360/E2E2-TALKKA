package com.talkka.server.review.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusReviewRepository extends JpaRepository<BusReviewEntity, Long> {

	public Optional<List<BusReviewEntity>> findAllByUserIdAndRouteIdAndStationIdAndTimeSlot(
		Long userId,
		Long routeId,
		Long stationId,
		Integer timeSlot);
}
