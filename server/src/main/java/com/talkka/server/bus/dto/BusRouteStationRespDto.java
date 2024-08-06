package com.talkka.server.bus.dto;

import java.time.LocalDateTime;

import com.talkka.server.bus.dao.BusRouteStationEntity;

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
public class BusRouteStationRespDto {

	private Long busRouteStationId;
	private BusRouteRespDto route;
	private BusStationRespDto station;
	private Short stationSeq;
	private String stationName;
	private LocalDateTime createdAt;

	public static BusRouteStationRespDto of(BusRouteStationEntity busRouteStationEntity) {
		return BusRouteStationRespDto.builder()
			.busRouteStationId(busRouteStationEntity.getId())
			.route(BusRouteRespDto.of(busRouteStationEntity.getRoute()))
			.station(BusStationRespDto.of(busRouteStationEntity.getStation()))
			.stationSeq(busRouteStationEntity.getStationSeq())
			.stationName(busRouteStationEntity.getStationName())
			.createdAt(busRouteStationEntity.getCreatedAt())
			.build();
	}
}
