package com.talkka.server.bus.dto;

import com.talkka.server.bus.dao.BusRouteEntity;
import com.talkka.server.bus.enums.BusRouteType;
import com.talkka.server.bus.enums.DistrictCode;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BusRouteCreateDto(
	@NotNull
	String apiRouteId,        // 공공 api 에서 제공해주는 식별자
	@NotNull
	String routeName,       // 노선 번호
	@NotNull
	@Schema(implementation = BusRouteType.class)
	BusRouteType routeTypeCd,        // 노선 유형 코드
	@NotNull
	String routeTypeName,   // 노선 유형명
	@NotNull
	String companyId,       // 운수업체 아이디
	@NotNull
	String companyName,     // 운수업체명
	@NotNull
	String companyTel,      // 운수업체 전화번호
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
