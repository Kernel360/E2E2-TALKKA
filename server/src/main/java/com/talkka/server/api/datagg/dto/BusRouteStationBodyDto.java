package com.talkka.server.api.datagg.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * @param stationId  정류소 아이디
 * @param stationSeq  정류소 순번
 * @param stationName  정류소 명칭
 * @param mobileNo  정류소 고유 모바일 번호
 * @param regionName  정류소 위치 지역명
 * @param districtCd  노선 관할 지역 코드
 * @param centerYn  중앙차로 여부 (N: 일반, Y: 중앙차로)
 * @param turnYn  회차점 여부 (N: 일반, Y: 회차점)
 * @param x  정류소 X 좌표
 * @param y  정류소 Y 좌표 */
@JacksonXmlRootElement(localName = "busRouteStationList")
public record BusRouteStationBodyDto(Long stationId, Integer stationSeq, String stationName, String mobileNo,
									 String regionName, String districtCd, String centerYn, String turnYn, Double x,
									 Double y) {
}
