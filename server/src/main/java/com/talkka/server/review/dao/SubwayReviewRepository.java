package com.talkka.server.review.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talkka.server.common.enums.TimeSlot;
import com.talkka.server.subway.enums.Updown;

@Repository
public interface SubwayReviewRepository extends JpaRepository<SubwayReviewEntity, Long> {

	List<SubwayReviewEntity> findAllByStationIdOrderByCreatedAtDesc(Long subwayId);

	List<SubwayReviewEntity> findAllByStationIdAndUpdownOrderByCreatedAtDesc(Long subwayId, Updown updown);

	List<SubwayReviewEntity> findAllByStationIdAndUpdownAndTimeSlotOrderByCreatedAtDesc(
		Long subwayId, Updown updown, TimeSlot timeSlot);
}
