package com.talkka.server.bus.dto;

import java.math.BigDecimal;

import com.talkka.server.bus.dao.BusStationEntity;
import com.talkka.server.bus.enums.CenterStation;
import com.talkka.server.bus.enums.DistrictCode;
import com.talkka.server.bus.enums.TurnStation;

import lombok.Builder;

@Builder
public record BusStationCreateDto(
	String apiStationId,
	String stationName,
	String regionName,
	DistrictCode districtCd,
	CenterStation centerYn,
	TurnStation turnYn,
	BigDecimal longitude,
	BigDecimal latitude
) {
	public BusStationEntity toEntity() {
		return BusStationEntity.builder()
			.apiStationId(apiStationId)
			.stationName(stationName)
			.regionName(regionName)
			.districtCd(districtCd)
			.centerYn(centerYn)
			.turnYn(turnYn)
			.longitude(longitude)
			.latitude(latitude)
			.build();
	}
}
