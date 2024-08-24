package com.talkka.server.bus.dto;

import com.talkka.server.bus.dao.BusStatEntity;
import com.talkka.server.bus.enums.PlateType;

public record BusStatRespDto(
	Integer beforeSeat,
	Integer afterSeat,
	Integer seatDiff,
	PlateType plateType,
	Integer dayOfWeek,
	Integer time
) {
	public static BusStatRespDto of(BusStatEntity busStatEntity) {
		return new BusStatRespDto(
			busStatEntity.getBeforeSeat(),
			busStatEntity.getAfterSeat(),
			busStatEntity.getSeatDiff(),
			busStatEntity.getPlateType(),
			busStatEntity.getDayOfWeek(),
			busStatEntity.getTime()
		);
	}
}
