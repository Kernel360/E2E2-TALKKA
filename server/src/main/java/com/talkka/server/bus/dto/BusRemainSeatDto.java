package com.talkka.server.bus.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.talkka.server.bus.dao.BusRemainSeatEntity;
import com.talkka.server.bus.enums.PlateType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class BusRemainSeatDto {
	@Getter
	@AllArgsConstructor
	private static class SeatInfo implements Serializable {
		LocalDateTime arrivedTime;
		Integer remainSeat;
	}

	private PlateType plateType;
	private String plateNo;
	private LocalDateTime standardTime;
	private List<SeatInfo> remainSeatList;

	public BusRemainSeatDto(List<BusRemainSeatEntity> seats) {
		remainSeatList = new ArrayList<>();

		for (var seat : seats) {
			plateType = seat.getPlateType();
			plateNo = seat.getPlateNo();
			remainSeatList.add(new SeatInfo(seat.getCreatedAt(), seat.getEmptySeat()));
		}
		if (!seats.isEmpty()) {
			standardTime = seats.get(seats.size() >> 1).getCreatedAt();
		}
	}
}
