package com.talkka.server.bus.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.talkka.server.bus.dao.BusRouteStationRepository;
import com.talkka.server.bus.dto.BusLiveInfoRespDto;
import com.talkka.server.bus.dto.BusRouteStationRespDto;
import com.talkka.server.bus.dto.BusStaticsDto;
import com.talkka.server.bus.enums.PlateType;
import com.talkka.server.bus.exception.BusRouteStationNotFoundException;
import com.talkka.server.common.enums.TimeSlot;
import com.talkka.server.common.exception.enums.InvalidTimeSlotEnumException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusLiveInfoServiceImpl implements BusLiveInfoService {
	private final BusArrivalService busArrivalService;
	private final BusStaticsService busStaticsService;
	private final BusRouteStationRepository busRouteStationRepository;

	/**
	 * 버스 실시간 정보를 조회합니다.
	 * <p>
	 * 기본 설정:
	 * <p>
	 * STATION_NUM = 2 (앞 뒤로 캡쳐할 정거장 수)
	 * <p>
	 * TIME_RANGE_MINUTE = 20 (앞 뒤로 캡쳐할 시간)
	 * <p>
	 * WEEK = 2 (몇 주 전 데이터까지 조회할지)
	 * @param routeStationId 기준 노선정거장 ID
	 * @return BusLiveInfoRespDto
	 */
	@Override
	public BusLiveInfoRespDto getBusLiveInfo(Long routeStationId, TimeSlot timeSlot, Long week) {
		final int STATION_NUM = 2;
		final int TIME_RANGE_MINUTE = 15;

		return this.getBusLiveInfo(routeStationId, STATION_NUM, timeSlot, TIME_RANGE_MINUTE, week);
	}

	@Override
	public BusLiveInfoRespDto getBusLiveInfo(Long routeStationId, String timeSlotString, Long week) {
		if (timeSlotString == null) {
			throw new InvalidTimeSlotEnumException();
		}
		// NOTE: REFACTOR THIS!!!
		if (week <= 0 || week > 5) {
			week = 1L;
		}
		return this.getBusLiveInfo(routeStationId, TimeSlot.valueOfEnumString(timeSlotString), week);
	}

	@Override
	public BusLiveInfoRespDto getBusLiveInfo(Long routeStationId, Integer stationNum, TimeSlot timeSlot,
		Integer timeRangeMinute, Long week) {
		var busRouteStation = busRouteStationRepository.findById(routeStationId)
			.orElseThrow(() -> new BusRouteStationNotFoundException(routeStationId));
		var route = busRouteStation.getRoute();
		var station = busRouteStation.getStation();
		var routeId = route.getId();
		var routeName = route.getRouteName();
		var apiRouteId = route.getApiRouteId();
		var apiStationId = station.getApiStationId();

		LocalDateTime requestedTime = timeSlot.getDateTime();
		LocalDateTime liveTime = LocalDateTime.now();

		var arrivalInfo = busArrivalService.getBusArrivalInfo(routeStationId, apiRouteId, apiStationId)
			.orElse(null);
		var requestedStatics = busStaticsService.getRouteStationStatics(routeStationId, stationNum, requestedTime,
			timeRangeMinute,
			week);

		Integer predicted = null;
		if (arrivalInfo != null) {
			var liveStatics = busStaticsService.getRouteStationStatics(routeStationId, 5, liveTime,
				timeRangeMinute, week);
			var plateType = arrivalInfo.plateType1();
			var locationNo = arrivalInfo.locationNo1();
			predicted = predictBusArrivalSeatsDiff(plateType, locationNo, liveStatics, 5)
				.map(diff -> diff + arrivalInfo.remainSeatCnt1())
				.map(diff -> diff < 0 ? 0 : diff)
				.orElse(null);
		}
		return BusLiveInfoRespDto.create(
			routeId,
			routeName,
			BusRouteStationRespDto.of(busRouteStation),
			arrivalInfo,
			requestedStatics,
			predicted
		);
	}

	private Optional<Integer> predictBusArrivalSeatsDiff(PlateType plateType, Integer locationNo,
		BusStaticsDto statics, Integer stationNum) {
		if (locationNo == null || locationNo < 0 || locationNo > stationNum) {
			return Optional.empty();
		}
		if (statics == null || statics.getData().isEmpty()) {
			return Optional.empty();
		}

		// statics 에서 plateType 이 같은 data만 filter 함.
		var filtered = statics.getData().stream()
			.filter(data -> data.getPlateType().equals(plateType))
			.toList();

		if (filtered.isEmpty()) {
			return Optional.empty();
		}

		// filtered 된 list 에서 station별 remainSeats의 평균을 구함
		List<Integer> averageRemainSeats = new ArrayList<>();
		var size = statics.getStationList().size();
		for (int i = 0; i < size; i++) {
			Integer sum = 0;
			for (var data : filtered) {
				Integer remainSeat = data.getRemainSeatList().get(i).getRemainSeat();
				sum += remainSeat;
			}
			averageRemainSeats.add(sum / filtered.size());
		}

		var locationIndex = stationNum - locationNo;
		var stationIndex = stationNum;
		var avgDiff = averageRemainSeats.get(stationIndex) - averageRemainSeats.get(locationIndex);
		return Optional.of(avgDiff);
	}
}
