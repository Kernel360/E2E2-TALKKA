package com.talkka.server.bus.dto;

import com.talkka.server.bus.dao.BusRouteEntity;
import com.talkka.server.bus.enums.BusRouteType;
import com.talkka.server.bus.enums.DistrictCode;

import lombok.Builder;

@Builder
public record BusRouteRespDto(
	Long routeId,
	String apiRouteId,
	String routeName,       // 노선 번호
	BusRouteType routeTypeCd,        // 노선 유형 코드
	String routeTypeName,   // 노선 유형명
	DistrictCode districtCd,         // 관할 지역 코드
	String upFirstTime,     // 평일 기점 첫차 시간
	String upLastTime,      // 평일 기점 막차 시간
	String downFirstTime,   // 평일 종점 첫차 시간
	String downLastTime,    // 평일 종점 막차 시간
	String startMobileNo,   // 기점 정류소 번호
	Long startStationId,    // 기점 정류소 아이디
	String startStationName, // 기점 정류소명
	Long endStationId,      // 종점 정류소 아이디
	String endMobileNo,     // 종점 정류소 번호
	String endStationName,  // 종점 정류소명
	String regionName,      // 지역명
	Integer peekAlloc,          // 평일 최소 배차 시간
	Integer nPeekAlloc          // 평일 최대 배차 시간
) {

	public static BusRouteRespDto of(BusRouteEntity busRouteEntity) {
		return new BusRouteRespDto(
			busRouteEntity.getId(),
			busRouteEntity.getApiRouteId(),
			busRouteEntity.getRouteName(),
			busRouteEntity.getRouteTypeCd(),
			busRouteEntity.getRouteTypeName(),
			busRouteEntity.getDistrictCd(),
			busRouteEntity.getUpFirstTime(),
			busRouteEntity.getUpLastTime(),
			busRouteEntity.getDownFirstTime(),
			busRouteEntity.getDownLastTime(),
			busRouteEntity.getStartMobileNo(),
			busRouteEntity.getStartStationId(),
			busRouteEntity.getStartStationName(),
			busRouteEntity.getEndStationId(),
			busRouteEntity.getEndMobileNo(),
			busRouteEntity.getEndStationName(),
			busRouteEntity.getRegionName(),
			busRouteEntity.getPeekAlloc(),
			busRouteEntity.getNPeekAlloc()
		);
	}
}
