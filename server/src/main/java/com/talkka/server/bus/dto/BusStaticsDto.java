package com.talkka.server.bus.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.talkka.server.bus.dao.BusRemainSeatEntity;
import com.talkka.server.bus.dao.BusRouteStationEntity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BusStaticsDto {

	@Getter
	@AllArgsConstructor
	private static class StationInfo implements Serializable {
		@NotNull
		Long stationId;
		@NotNull
		String stationName;
	}

	@NotNull
	private LocalDateTime requestTime;
	@NotNull
	private Long routeId;
	@NotNull
	private String routeName;
	@NotNull
	private Integer stationNum;
	@NotNull
	private Integer busNum;
	@NotNull
	private List<StationInfo> stationList;
	@NotNull
	private List<BusRemainSeatDto> data;

	public BusStaticsDto(LocalDateTime requestTime, List<BusRouteStationEntity> routeStationList,
		List<List<BusRemainSeatEntity>> data) {
		this.requestTime = requestTime;
		this.stationList = new ArrayList<>();
		for (var rs : routeStationList) {
			stationList.add(new StationInfo(rs.getStation().getId(), rs.getStation().getStationName()));
			this.routeId = rs.getRoute().getId();
			this.routeName = rs.getRoute().getRouteName();
		}
		this.stationNum = routeStationList.size();
		this.busNum = data.size();
		this.data = data.stream()
			.map(BusRemainSeatDto::new)
			.sorted(Comparator.comparing(BusRemainSeatDto::getStandardTime))
			.toList();
	}
}
