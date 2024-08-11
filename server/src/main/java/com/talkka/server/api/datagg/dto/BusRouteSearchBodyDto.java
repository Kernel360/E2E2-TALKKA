package com.talkka.server.api.datagg.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * @param routeId  노선 아이디
 * @param routeName  노선 번호
 * @param routeTypeCd  노선 유형 코드
 * @param routeTypeName  노선 유형명
 * @param regionName  노선 운행 지역
 * @param districtCd  노선 관할 지역 코드 */
@JacksonXmlRootElement(localName = "busRouteList")
public record BusRouteSearchBodyDto(Long routeId, String routeName, String routeTypeCd, String routeTypeName,
									String regionName, int districtCd) {
}
