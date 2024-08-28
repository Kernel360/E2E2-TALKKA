package com.talkka.server.bus.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.talkka.server.bus.dao.BusRemainSeatEntity;
import com.talkka.server.bus.dao.BusRouteStationEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BusViewDto {

	@Getter
	@AllArgsConstructor
	private static class StationInfo implements Serializable {
		Long stationId;
		String stationName;
	}

	private LocalDateTime requestTime;
	private Long routeId;
	private String routeName;
	private Integer stationNum;
	private Integer busNum;
	private List<StationInfo> stationList;
	private List<BusRemainSeatDto> data;

	public BusViewDto(LocalDateTime requestTime, List<BusRouteStationEntity> routeStationList,
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
