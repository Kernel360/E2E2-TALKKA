package com.talkka.server.bus.dto;

import com.talkka.server.bus.dao.BusRouteEntity;
import com.talkka.server.bus.enums.BusRouteType;
import com.talkka.server.bus.enums.DistrictCode;

import lombok.Builder;

@Builder
public record BusRouteCreateDto(
	String apiRouteId,        // 공공 api 에서 제공해주는 식별자
	String routeName,       // 노선 번호
	BusRouteType routeTypeCd,        // 노선 유형 코드
	String routeTypeName,   // 노선 유형명
	String companyId,       // 운수업체 아이디
	String companyName,     // 운수업체명
	String companyTel,      // 운수업체 전화번호
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
	public BusRouteEntity toEntity() {
		return BusRouteEntity.builder()
			.apiRouteId(apiRouteId)
			.routeName(routeName)
			.routeTypeCd(routeTypeCd)
			.routeTypeName(routeTypeName)
			.companyId(companyId)
			.companyName(companyName)
			.companyTel(companyTel)
			.districtCd(districtCd)
			.upFirstTime(upFirstTime)
			.upLastTime(upLastTime)
			.downFirstTime(downFirstTime)
			.downLastTime(downLastTime)
			.startMobileNo(startMobileNo)
			.startStationId(startStationId)
			.startStationName(startStationName)
			.endStationId(endStationId)
			.endMobileNo(endMobileNo)
			.endStationName(endStationName)
			.regionName(regionName)
			.peekAlloc(peekAlloc)
			.nPeekAlloc(nPeekAlloc)
			.build();
	}
}
