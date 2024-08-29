package com.talkka.server.bus.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.talkka.server.bus.dao.BusRemainSeatEntity;
import com.talkka.server.bus.enums.PlateType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class BusRemainSeatDto {
	@Getter
	@AllArgsConstructor
	public static class SeatInfo {
		@NotNull
		LocalDateTime arrivedTime;
		@NotNull
		Integer remainSeat;
	}

	@NotNull
	private PlateType plateType;
	@NotNull
	private String plateNo;
	@NotNull
	private LocalDateTime standardTime;
	@NotNull
	private final List<SeatInfo> remainSeatList = new ArrayList<>();

	public BusRemainSeatDto(List<BusRemainSeatEntity> seatList) {
		for (var seat : seatList) {
			plateType = seat.getPlateType();
			plateNo = seat.getPlateNo();
			remainSeatList.add(new SeatInfo(seat.getCreatedAt(), seat.getEmptySeat()));
		}
		if (!seatList.isEmpty()) {
			// 기준 정거장을 기준으로 양 옆으로 조회하기 때문에
			// 기준 정거장은 배열의 정 가운데에 위치함
			int middleIdx = seatList.size() / 2;
			standardTime = seatList.get(middleIdx).getCreatedAt();
		}
	}
}
