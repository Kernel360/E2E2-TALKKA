package com.talkka.server.review.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BusReviewRepository extends JpaRepository<BusReviewEntity, Long> {
	// 실제로 조회할 경우, 해당 쿼리를 항상 사용함. 따라서 네이밍을 findReviews로 변경
	@Query(value = "SELECT * FROM bus_review WHERE user_id = :userId AND route_id = :routeId AND bus_route_station_id = :busRouteStationId AND time_slot = :timeSlot", nativeQuery = true)
	List<BusReviewEntity> findReviews(
		Long userId,
		Long routeId,
		Long busRouteStationId,
		Integer timeSlot);
}