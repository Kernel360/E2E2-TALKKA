package com.talkka.server.bus.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.talkka.server.bus.dao.BusStationEntity;
import com.talkka.server.bus.enums.CenterStation;
import com.talkka.server.bus.enums.DistrictCode;
import com.talkka.server.bus.enums.TurnStation;

import lombok.Builder;

@Builder
public record BusStationRespDto(
	Long stationId,
	String stationName,
	String regionName,
	DistrictCode districtCd,
	CenterStation centerYn,
	TurnStation turnYn,
	BigDecimal longitude,
	BigDecimal latitude,
	LocalDateTime createdAt
) {
	public static BusStationRespDto of(BusStationEntity busStationEntity) {
		return new BusStationRespDto(
			busStationEntity.getId(),
			busStationEntity.getStationName(),
			busStationEntity.getRegionName(),
			busStationEntity.getDistrictCd(),
			busStationEntity.getCenterYn(),
			busStationEntity.getTurnYn(),
			busStationEntity.getLongitude(),
			busStationEntity.getLatitude(),
			busStationEntity.getCreatedAt()
		);
	}
}
