package com.talkka.server.api.datagg.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JacksonXmlRootElement(localName = "busRouteStationList")
public class BusRouteStationBodyDto {
	private Long stationId;          // 정류소 아이디
	private Integer stationSeq;      // 정류소 순번
	private String stationName;      // 정류소 명칭
	private String mobileNo;         // 정류소 고유 모바일 번호
	private String regionName;       // 정류소 위치 지역명
	private String districtCd;      // 노선 관할 지역 코드
	private String centerYn;         // 중앙차로 여부 (N: 일반, Y: 중앙차로)
	private String turnYn;           // 회차점 여부 (N: 일반, Y: 회차점)
	private Double x;                // 정류소 X 좌표
	private Double y;                // 정류소 Y 좌표
}
