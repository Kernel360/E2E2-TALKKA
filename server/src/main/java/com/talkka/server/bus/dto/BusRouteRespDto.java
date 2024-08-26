package com.talkka.server.bus.dto;

import com.talkka.server.bus.dao.BusRouteEntity;
import com.talkka.server.bus.enums.BusRouteType;
import com.talkka.server.bus.enums.DistrictCode;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BusRouteRespDto(
	@NotNull
	Long routeId,
	@NotNull
	String apiRouteId,
	@NotNull
	String routeName,       // 노선 번호
	@NotNull
	@Schema(implementation = BusRouteType.class)
	BusRouteType routeTypeCd,        // 노선 유형 코드
	@NotNull
	String routeTypeName,   // 노선 유형명
	@NotNull
	@Schema(implementation = DistrictCode.class)
	DistrictCode districtCd,         // 관할 지역 코드
	@NotNull
	String upFirstTime,     // 평일 기점 첫차 시간
	@NotNull
	String upLastTime,      // 평일 기점 막차 시간
	@NotNull
	String downFirstTime,   // 평일 종점 첫차 시간
	@NotNull
	String downLastTime,    // 평일 종점 막차 시간
	@NotNull
	String startMobileNo,   // 기점 정류소 번호
	@NotNull
	Long startStationId,    // 기점 정류소 아이디
	@NotNull
	String startStationName, // 기점 정류소명
	@NotNull
	Long endStationId,      // 종점 정류소 아이디
	@NotNull
	String endMobileNo,     // 종점 정류소 번호
	@NotNull
	String endStationName,  // 종점 정류소명
	@NotNull
	String regionName,      // 지역명
	@NotNull
	Integer peekAlloc,          // 평일 최소 배차 시간
	@NotNull
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
