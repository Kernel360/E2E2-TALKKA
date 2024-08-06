package com.talkka.server.bus.dto;

import com.talkka.server.bus.dao.BusStationEntity;

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
public class BusRouteStationCreateDto {

	private String apiRouteId;
	private String apiStationId;
	private BusStationEntity station;
	private Short stationSeq;
	private String stationName;
}
