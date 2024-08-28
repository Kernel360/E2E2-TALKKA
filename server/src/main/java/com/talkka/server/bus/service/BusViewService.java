package com.talkka.server.bus.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.talkka.server.bus.dao.BusRemainSeatEntity;
import com.talkka.server.bus.dao.BusRemainSeatRepository;
import com.talkka.server.bus.dao.BusRouteStationEntity;
import com.talkka.server.bus.dao.BusRouteStationRepository;
import com.talkka.server.bus.dto.BusViewDto;
import com.talkka.server.bus.exception.BusRouteNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusViewService {
	private final BusRemainSeatRepository busRemainSeatRepository;
	private final BusRouteStationRepository busRouteStationRepository;

	/**
	 * 버스 경로 정보를 조회합니다.
	 *
	 * @param routeStationId  기준 노선정거장 ID
	 * @param stationNum      현재 정거장에서 전후 몇 정거장을 조회할지 설정
	 *                        ex) stationNum = 3 -> 기준 노선정거장 3개 전~3개 후 조회
	 * @param time            기준 시간
	 * @param timeRangeMinute 기준 시간 전후 몇 분까지 조회할지 설정
	 *                        ex) timeRangeMinute = 3 -> 기준 시간 3분 전~3분 후 조회
	 * @param week            몇 주 전의 데이터를 조회할지 설정
	 *                        ex) week = 2 -> 2주 전 같은 요일의 데이터를 조회
	 * @return BusRouteInfoRespDto 버스 경로 정보
	 */
	public BusViewDto getBusView(
		Long routeStationId,
		Integer stationNum,
		LocalDateTime time,
		Integer timeRangeMinute,
		Long week
	) {
		// 타겟 노선정거장 조회
		var routeStation = busRouteStationRepository.findById(routeStationId)
			.orElseThrow(() -> new BusRouteNotFoundException(routeStationId));
		Long routeId = routeStation.getRoute().getId();
		Long stationId = routeStation.getStation().getId();
		// 타겟 노선이 지나가는 모든 노선정거장 조회
		var routeStationList = busRouteStationRepository.findAllByRouteId(routeId)
			.stream()
			.filter(rs -> Math.abs(rs.getStationSeq() - routeStation.getStationSeq()) <= stationNum)
			.sorted(Comparator.comparing(BusRouteStationEntity::getStationSeq))
			.toList();

		// 새벽 3시까지는 전날로 침
		long epochDay = time.getHour() < 3 ? time.toLocalDate().toEpochDay() - 1 : time.toLocalDate().toEpochDay();
		var timeIntervals = getTimeInterval(time, timeRangeMinute);

		// 조회 조건에 맞는 버스 도착 정보 가져옴
		List<BusRemainSeatEntity> busSeats = new ArrayList<>();
		for (var timeInterval : timeIntervals) {
			busSeats.addAll(
				busRemainSeatRepository.findByRouteIdAndStationIdAndEpochDayAndTimeBetween(
					routeId,
					stationId,
					epochDay - 7 * week,
					timeInterval[0],
					timeInterval[1]
				)
			);
		}
		List<List<BusRemainSeatEntity>> data = new ArrayList<>();
		for (var seat : busSeats) {
			// 타겟 정거장 전후 도착정보
			data.add(
				busRemainSeatRepository.findByPlateStatisticAndStationSeqBetweenOrderByStationSeq(
					seat.getPlateStatistic(),
					seat.getStationSeq() - stationNum,
					seat.getStationSeq() + stationNum
				)
			);
		}
		return new BusViewDto(
			time,
			routeStationList,
			data
		);
	}

	/**
	 * 시간 구간이 0시를 넘어가는 경우, 두 개의 구간으로 나누어 검색을 수행합니다.
	 * 예를 들어, 23:43에서 00:13 사이의 시간 구간은 23:43-23:59 및 00:00-00:13 두 개의 구간으로 나누어집니다.
	 *
	 * @param time      기준 시간으로, 이 시간으로부터 {@code timeRange} 분 전후의 구간을 계산합니다.
	 * @param timeRange 기준 시간에서 검색할 분 단위의 시간 범위입니다.
	 * @return 시간 구간을 나타내는 {@code List<Integer[]>}를 반환합니다.
	 * 각 배열은 [시작 시간, 종료 시간] 형식으로 시간 구간을 나타냅니다.
	 * 시간이 0시를 넘어가는 경우, 두 개의 구간으로 나누어 반환합니다.
	 */
	private List<Integer[]> getTimeInterval(LocalDateTime time, Integer timeRange) {
		List<Integer[]> intervals = new ArrayList<>();
		int startTime =
			time.minusMinutes(timeRange).getHour() * 100 + time.minusMinutes(timeRange).getMinute();
		int endTime = time.plusMinutes(timeRange).getHour() * 100 + time.plusMinutes(timeRange).getMinute();
		if (startTime < endTime) {
			intervals.add(new Integer[] {startTime, endTime});
		} else {
			intervals.add(new Integer[] {startTime, 2359});
			intervals.add(new Integer[] {0, endTime});
		}
		return intervals;
	}
}
