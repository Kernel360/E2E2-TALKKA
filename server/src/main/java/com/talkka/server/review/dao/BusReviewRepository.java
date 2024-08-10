package com.talkka.server.review.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talkka.server.review.enums.BusTimeSlot;

@Repository
public interface BusReviewRepository extends JpaRepository<BusReviewEntity, Long> {
	// 실제로 조회할 경우, 해당 쿼리를 항상 사용함. 따라서 네이밍을 findReviews로 변경
	List<BusReviewEntity> findAllByWriterIdAndRouteIdAndStationIdAndBusTimeSlot(
		Long userId,
		Long routeId,
		Long busRouteStationId,
		BusTimeSlot busTimeSlot);
}
