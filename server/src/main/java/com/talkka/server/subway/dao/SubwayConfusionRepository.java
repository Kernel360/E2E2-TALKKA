package com.talkka.server.subway.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talkka.server.common.enums.TimeSlot;
import com.talkka.server.subway.enums.DayType;
import com.talkka.server.subway.enums.Updown;

@Repository
public interface SubwayConfusionRepository extends JpaRepository<SubwayConfusionEntity, Long> {
	Optional<SubwayConfusionEntity> findBySubwayStationIdAndDayTypeAndUpdownAndTimeSlot(
		Long stationId, DayType dayType, Updown updown, TimeSlot timeSlot);
}
