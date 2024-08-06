package com.talkka.server.bus.dto;

import java.math.BigDecimal;

import com.talkka.server.bus.dao.BusStationEntity;
import com.talkka.server.bus.enums.CenterStation;
import com.talkka.server.bus.enums.DistrictCode;
import com.talkka.server.bus.enums.TurnStation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BusStationCreateDto {

	private String apiStationId;
	private String stationName;
	private String regionName;
	private DistrictCode districtCd;
	private CenterStation centerYn;
	private TurnStation turnYn;
	private BigDecimal longitude;
	private BigDecimal latitude;

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
