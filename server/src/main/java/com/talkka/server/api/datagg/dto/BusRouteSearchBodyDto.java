package com.talkka.server.api.datagg.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JacksonXmlRootElement(localName = "busRouteList")
public class BusRouteSearchBodyDto {
	private Long routeId;                     // 노선 아이디
	private String routeName;                   // 노선 번호
	private String routeTypeCd;                 // 노선 유형 코드
	private String routeTypeName;               // 노선 유형명
	private String regionName;                  // 노선 운행 지역
	private int districtCd;                     // 노선 관할 지역 코드
}
