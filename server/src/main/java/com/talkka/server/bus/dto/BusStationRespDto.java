package com.talkka.server.bus.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.talkka.server.bus.dao.BusStationEntity;
import com.talkka.server.bus.enums.CenterStation;
import com.talkka.server.bus.enums.DistrictCode;
import com.talkka.server.bus.enums.TurnStation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BusStationRespDto(
	@NotNull
	Long stationId,
	@NotNull
	String apiStationId,
	@NotNull
	String stationName,
	@NotNull
	String regionName,
	@NotNull
	@Schema(implementation = DistrictCode.class)
	DistrictCode districtCd,
	@NotNull
	@Schema(implementation = CenterStation.class)
	CenterStation centerYn,
	@NotNull
	@Schema(implementation = TurnStation.class)
	TurnStation turnYn,
	@NotNull
	BigDecimal longitude,
	@NotNull
	BigDecimal latitude,
	@NotNull
	LocalDateTime createdAt
) {
	public static BusStationRespDto of(BusStationEntity busStationEntity) {
		return new BusStationRespDto(
			busStationEntity.getId(),
			busStationEntity.getApiStationId(),
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
