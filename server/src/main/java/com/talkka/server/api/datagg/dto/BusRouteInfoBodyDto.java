package com.talkka.server.api.datagg.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JacksonXmlRootElement(localName = "busRouteInfoItem")
public class BusRouteInfoBodyDto {
	private String companyId;           // 운수업체 아이디
	private String companyName;         // 운수업체명
	private String companyTel;          // 운수업체 전화번호
	private int districtCd;             // 관할 지역 코드
	private String downFirstTime;       // 평일 종점 첫차 시간
	private String downLastTime;        // 평일 종점 막차 시간
	private Long endStationId;        // 종점 정류소 아이디
	private String endStationName;      // 종점 정류소명
	private int peekAlloc;              // 평일 최소 배차 시간
	private String regionName;          // 지역명
	private Long routeId;             // 노선 아이디
	private String routeName;           // 노선 번호
	private int routeTypeCd;            // 노선 유형 코드
	private String routeTypeName;       // 노선 유형명
	private String startMobileNo;       // 기점 정류소 번호
	private Long startStationId;      // 기점 정류소 아이디
	private String startStationName;    // 기점 정류소명
	private String upFirstTime;         // 평일 기점 첫차 시간
	private String upLastTime;          // 평일 기점 막차 시간
	private int nPeekAlloc;             // 평일 최대 배차 시간
}
