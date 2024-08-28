package com.talkka.server.api.datagg.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import jakarta.validation.constraints.NotNull;

@JacksonXmlRootElement(localName = "busArrivalList")
public record BusArrivalBodyDto(
	@NotNull
	Long stationId,            // 정류소아이디
	@NotNull
	Long routeId,             // 노선아이디
	@NotNull
	Integer locationNo1,            // 첫번째차량 위치 정보
	@NotNull
	Integer predictTime1,           // 첫번째차량 도착예상시간
	@NotNull
	Integer lowPlate1,          // 첫번째차량 저상버스여부
	@NotNull
	String plateNo1,            // 첫번째차량 차량번호
	@NotNull
	Integer remainSeatCnt1,         // 첫번째차량 빈자리 수
	@NotNull
	Integer locationNo2,            // 두번째차량 위치 정보
	@NotNull
	Integer predictTime2,           // 두번째차량 도착예상시간
	@NotNull
	Integer lowPlate2,          // 두번째차량 저상버스여부
	@NotNull
	String plateNo2,            // 두번째차량 차량번호
	@NotNull
	Integer remainSeatCnt2,         // 두번째차량 빈자리 수
	@NotNull
	Integer staOrder,               // 정류소 순번
	@NotNull
	String flag                  // 상태구분
) {
}
