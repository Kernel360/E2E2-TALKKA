package com.talkka.server.bus.dto;

import com.talkka.server.api.datagg.dto.BusArrivalBodyDto;
import com.talkka.server.bus.enums.PlateType;
import com.talkka.server.bus.enums.RunningStatus;

import jakarta.validation.constraints.NotNull;

public record BusArrivalRespDto(
	@NotNull
	Integer locationNo1,            // 첫번째차량 위치 정보
	@NotNull
	Integer predictTime1,           // 첫번째차량 도착예상시간
	@NotNull
	String plateNo1,            // 첫번째차량 차량번호
	@NotNull
	Integer remainSeatCnt1,         // 첫번째차량 빈자리 수
	@NotNull
	Integer locationNo2,            // 두번째차량 위치 정보
	@NotNull
	Integer predictTime2,           // 두번째차량 도착예상시간
	@NotNull
	String plateNo2,            // 두번째차량 차량번호
	@NotNull
	Integer remainSeatCnt2,         // 두번째차량 빈자리 수
	@NotNull
	PlateType plateType1,        // 첫번째차량 차종
	@NotNull
	PlateType plateType2,        // 두번째차량 차종
	@NotNull
	RunningStatus flag                  // 상태구분
) {
	public static BusArrivalRespDto of(BusArrivalBodyDto busArrivalBodyDto, PlateType plateType1,
		PlateType plateType2) {
		return new BusArrivalRespDto(
			busArrivalBodyDto.locationNo1(),
			busArrivalBodyDto.predictTime1(),
			busArrivalBodyDto.plateNo1(),
			busArrivalBodyDto.remainSeatCnt1(),
			busArrivalBodyDto.locationNo2(),
			busArrivalBodyDto.predictTime2(),
			busArrivalBodyDto.plateNo2(),
			busArrivalBodyDto.remainSeatCnt2(),
			plateType1,
			plateType2,
			RunningStatus.valueOf(busArrivalBodyDto.flag())
		);
	}
}
