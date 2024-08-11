package com.talkka.server.api.datagg.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "busRouteInfoItem")
public record BusRouteInfoBodyDto(
	String companyId,           // 운수업체 아이디
	String companyName,         // 운수업체명
	String companyTel,          // 운수업체 전화번호
	int districtCd,             // 관할 지역 코드
	String downFirstTime,       // 평일 종점 첫차 시간
	String downLastTime,        // 평일 종점 막차 시간
	Long endStationId,        // 종점 정류소 아이디
	String endStationName,      // 종점 정류소명
	int peekAlloc,              // 평일 최소 배차 시간
	String regionName,          // 지역명
	Long routeId,             // 노선 아이디
	String routeName,           // 노선 번호
	int routeTypeCd,            // 노선 유형 코드
	String routeTypeName,       // 노선 유형명
	String startMobileNo,       // 기점 정류소 번호
	Long startStationId,      // 기점 정류소 아이디
	String startStationName,    // 기점 정류소명
	String upFirstTime,         // 평일 기점 첫차 시간
	String upLastTime,          // 평일 기점 막차 시간
	int nPeekAlloc            // 평일 최대 배차 시간
) {
}
