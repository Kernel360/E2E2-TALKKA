package com.talkka.server.bus.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
public class BusStationRespDto {

	private Long stationId;
	private String stationName;
	private String regionName;
	private DistrictCode districtCd;
	private CenterStation centerYn;
	private TurnStation turnYn;
	private BigDecimal longitude;
	private BigDecimal latitude;
	private LocalDateTime createdAt;

	public static BusStationRespDto of(BusStationEntity busStationEntity) {
		return BusStationRespDto.builder()
			.stationId(busStationEntity.getId())
			.stationName(busStationEntity.getStationName())
			.regionName(busStationEntity.getRegionName())
			.districtCd(busStationEntity.getDistrictCd())
			.centerYn(busStationEntity.getCenterYn())
			.turnYn(busStationEntity.getTurnYn())
			.longitude(busStationEntity.getLongitude())
			.latitude(busStationEntity.getLatitude())
			.createdAt(busStationEntity.getCreatedAt())
			.build();
	}
}
