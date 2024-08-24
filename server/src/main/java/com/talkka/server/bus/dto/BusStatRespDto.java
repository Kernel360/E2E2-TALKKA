package com.talkka.server.bus.dto;

import java.time.LocalDateTime;

import com.talkka.server.bus.dao.BusStatEntity;
import com.talkka.server.bus.enums.PlateType;

public record BusStatRespDto(
	Long statId,
	Long routeId,
	Long stationId,
	String apiRouteId,
	String apiStationId,
	Integer beforeSeat,
	Integer afterSeat,
	Integer seatDiff,
	LocalDateTime beforeTime,
	LocalDateTime afterTime,
	String plateNo,
	PlateType plateType,
	Integer dayOfWeek,
	Integer hour,
	Integer minute,
	LocalDateTime createdAt
) {
	public static BusStatRespDto of(BusStatEntity busStatEntity) {
		return new BusStatRespDto(
			busStatEntity.getId(),
			busStatEntity.getRoute().getId(),
			busStatEntity.getStation().getId(),
			busStatEntity.getApiRouteId(),
			busStatEntity.getApiStationId(),
			busStatEntity.getBeforeSeat(),
			busStatEntity.getAfterSeat(),
			busStatEntity.getSeatDiff(),
			busStatEntity.getBeforeTime(),
			busStatEntity.getAfterTime(),
			busStatEntity.getPlateNo(),
			busStatEntity.getPlateType(),
			busStatEntity.getDayOfWeek(),
			busStatEntity.getHour(),
			busStatEntity.getMinute(),
			busStatEntity.getCreatedAt()
		);
	}
}
