package com.talkka.server.bus.dto;

import java.math.BigDecimal;

import com.talkka.server.bus.dao.BusStationEntity;
import com.talkka.server.bus.enums.CenterStation;
import com.talkka.server.bus.enums.DistrictCode;
import com.talkka.server.bus.enums.TurnStation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BusStationCreateDto(
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
