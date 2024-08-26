package com.talkka.server.subway.dao;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talkka.server.subway.enums.DayType;
import com.talkka.server.subway.enums.Updown;

@Repository
public interface SubwayTimetableRepository extends JpaRepository<SubwayTimetableEntity, Long> {

	Optional<SubwayTimetableEntity> findBySubwayStationIdAndDayTypeAndUpdownAndArrivalTime(
		Long stationId, DayType dayType, Updown updown, LocalTime time
	);

	List<SubwayTimetableEntity> findBySubwayStationIdAndDayTypeAndUpdownAndArrivalTimeBetween(
		Long stationId, DayType dayType, Updown updown, LocalTime startTime, LocalTime endTime
	);
}
