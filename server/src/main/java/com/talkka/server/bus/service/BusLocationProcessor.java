package com.talkka.server.bus.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talkka.server.bus.dao.BusLocationEntity;
import com.talkka.server.bus.dao.BusPlateStatisticEntity;
import com.talkka.server.bus.dao.BusPlateStatisticRepository;
import com.talkka.server.bus.dao.BusRemainSeatEntity;
import com.talkka.server.bus.dao.BusRouteEntity;
import com.talkka.server.bus.dao.BusRouteRepository;
import com.talkka.server.bus.dao.BusRouteStationEntity;
import com.talkka.server.bus.dao.BusRouteStationRepository;
import com.talkka.server.bus.dao.BusStationEntity;
import com.talkka.server.bus.dao.BusStationRepository;
import com.talkka.server.bus.enums.PlateType;
import com.talkka.server.bus.exception.InvalidLocationNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BusLocationProcessor {
	private final BusRouteRepository busRouteRepository;
	private final BusStationRepository busStationRepository;
	private final BusRouteStationRepository busRouteStationRepository;
	private final BusPlateStatisticRepository busPlateStatisticRepository;
	private final Map<String, Deque<BusLocationEntity>> locationMap = new HashMap<>();
	private final Map<String, Optional<BusRouteEntity>> routeMap = new HashMap<>();
	private final Map<String, Optional<BusStationEntity>> stationMap = new HashMap<>();
	private final Map<Long, List<BusRouteStationEntity>> routeStationMap = new HashMap<>();
	private final Map<String, Integer> routeLengthMap = new HashMap<>();
	private final List<BusPlateStatisticEntity> routeInfoList = new ArrayList<>();

	@Transactional
	public void start(List<BusLocationEntity> locations) {
		for (var location : locations) {
			try {
				if (locationMap.containsKey(location.getPlateNo())) {
					var dq = locationMap.get(location.getPlateNo());
					if (!dq.isEmpty() && location.getStationSeq() < dq.peekLast().getStationSeq()) {
						process(dq);
						dq = new ArrayDeque<>();
						locationMap.put(location.getPlateNo(), dq);
					}
					dq.add(location);
				} else {
					Deque<BusLocationEntity> dq = new ArrayDeque<>();
					dq.add(location);
					locationMap.put(location.getPlateNo(), dq);
				}
			} catch (InvalidLocationNotFoundException e) {
				log.warn("버스 위치정보 가공 실패 locationId : {}, apiRouteId : {}, apiStationId : {}, plateNo : {} ",
					location.getBusLocationId(),
					location.getApiRouteId(),
					location.getApiStationId(),
					location.getPlateNo());
			}
		}
		persist();
	}

	private void persist() {
		for (String key : locationMap.keySet()) {
			try {
				var tmp = locationMap.get(key);
				if (!tmp.isEmpty()) {
					process(tmp);
				}
			} catch (InvalidLocationNotFoundException e) {
				log.warn("버스 위치정보 가공 실패");
			}
		}
		busPlateStatisticRepository.saveAll(routeInfoList);
	}

	/**
	 * 특정 버스(plateNo)를 기준으로 수집된 데이터를 처리합니다.
	 * 빠져있는 정거장 정보를 양 옆 정거장의 평균 정보로 대치하여 전체 노선 데이터를 완성하고, 이를 {@code routeInfoList}에 추가합니다.
	 *
	 * @param locations 특정 버스의 위치 정보를 담고 있는 {@code Deque<BusLocationEntity>}입니다.
	 *                  이 정보는 특정 버스에 대한 다양한 정거장에서의 위치 데이터를 포함합니다.
	 */
	private void process(Deque<BusLocationEntity> locations) {
		assert locations.peek() != null;
		String apiRouteId = locations.peek().getApiRouteId();
		String apiStationId = locations.peek().getApiStationId();
		String plateNo = locations.peek().getPlateNo();
		LocalDateTime createdAt = locations.peek().getCreatedAt();
		List<BusRemainSeatEntity> result = new ArrayList<>();
		// 노선의 길이 확인
		int num = getRouteLength(apiRouteId);
		// 노선 손실률이 80% 이상이면 저장하지 않음
		if (locations.size() < num * 0.2) {
			return;
		}
		var routeStationList = getRouteStationByRouteId(apiRouteId);
		try {
			if (locations.peek().getStationSeq() != 1) {
				result.add(makeSeatEntity(Optional.empty(), Optional.of(locations.peek()), 1,
					routeStationList.get(0).getStation()));
			} else {
				result.add(toSeatEntity(locations.poll(), 1));
			}
			for (int i = 2; i <= num; i++) {
				BusLocationEntity cur = null;
				// StationSeq 가 중복되면 마지막것을 선택
				while (!locations.isEmpty() && locations.peek().getStationSeq() == i) {
					cur = locations.poll();
				}
				// 특정 정거장의 위치정보가 없거나 좌석정보가 없으면 양옆 정류장 데이터의 평균으로 대치
				if (cur == null || cur.getRemainSeatCount() == -1) {
					if (locations.isEmpty()) {
						result.add(makeSeatEntity(Optional.of(result.get(i - 2)), Optional.empty(), i,
							routeStationList.get(i - 1).getStation()));
					} else {
						result.add(makeSeatEntity(Optional.of(result.get(i - 2)), Optional.of(locations.peek()), i,
							routeStationList.get(i - 1).getStation()));
					}
				} else {
					result.add(toSeatEntity(cur, i));
				}
			}
		} catch (InvalidLocationNotFoundException e) {
			log.warn("버스 위치정보 가공 실패 : 노선({}), 정거장({}), 버스이름({}), 생성일({})",
				apiRouteId, apiStationId, plateNo, createdAt);
		}
		BusPlateStatisticEntity routeInfo = BusPlateStatisticEntity.builder()
			.route(result.get(0).getRoute())
			.plateNo(result.get(0).getPlateNo())
			.plateType(result.get(0).getPlateType())
			.epochDay(result.get(0).getEpochDay())
			.startTime(result.get(0).getTime())
			.endTime(result.get(result.size() - 1).getTime())
			.seats(result)
			.build();
		result.forEach(seat -> seat.updateRouteInfo(routeInfo));
		routeInfoList.add(routeInfo);
	}

	private BusRemainSeatEntity toSeatEntity(BusLocationEntity location, int stationSeq) {
		var route = getRoute(location.getApiRouteId());
		var station = getStation(location.getApiStationId());
		return BusRemainSeatEntity.build()
			.route(route)
			.station(station)
			.stationSeq(stationSeq)
			.emptySeat(location.getRemainSeatCount())
			.plateNo(location.getPlateNo())
			.plateType(location.getPlateType())
			.setCreateTime(location.getCreatedAt())
			.build();
	}

	/**
	 * 비어있는 엔티티가 있을 경우, 좌우 데이터를 비교하여 새로운 {@code BusRemainSeatEntity}를 생성합니다.
	 *
	 * <p>이 메서드는 이전 정거장과 이후 정거장의 데이터를 비교하여 평균값을 계산하거나,
	 * 기점/종점에 따른 특수 조건을 적용하여 새로운 좌석 정보를 생성합니다.</p>
	 *
	 * @param before     이전 정거장의 좌석 정보. {@code Optional<BusRemainSeatEntity>}로 제공되며,
	 *                   비어있을 수도 있습니다.
	 * @param after      이후 정거장의 위치 정보. {@code Optional<BusLocationEntity>}로 제공되며,
	 *                   비어있을 수도 있습니다.
	 * @param stationSeq 정거장의 순서 번호입니다.
	 * @param station    현재 정거장을 나타내는 {@code BusStationEntity}입니다.
	 * @return 새롭게 생성된 {@code BusRemainSeatEntity} 객체입니다.
	 * @throws InvalidLocationNotFoundException 이전 정거장과 이후 정거장 정보가 모두 없는 경우에 발생합니다.
	 */
	private BusRemainSeatEntity makeSeatEntity(Optional<BusRemainSeatEntity> before, Optional<BusLocationEntity> after,
		int stationSeq, BusStationEntity station) throws
		InvalidLocationNotFoundException {
		if (before.isPresent() && after.isPresent()) {
			var beforeEntity = before.get();
			var afterEntity = after.get();
			var route = beforeEntity.getRoute();
			return BusRemainSeatEntity.build()
				.route(route)
				.station(station)
				.stationSeq(stationSeq)
				.emptySeat((beforeEntity.getEmptySeat() + afterEntity.getRemainSeatCount()) / 2)
				.plateNo(beforeEntity.getPlateNo())
				.plateType(beforeEntity.getPlateType())
				.setCreateTime(getBetweenTime(beforeEntity.getCreatedAt(), afterEntity.getCreatedAt(),
					afterEntity.getStationSeq() - beforeEntity.getStationSeq()))
				.build();
		} else if (before.isPresent()) {
			var beforeEntity = before.get();
			int emptySeat = beforeEntity.getEmptySeat();
			// 종점에서는 모든 자리가 비어있음
			if (stationSeq == getRouteLength(beforeEntity.getRoute().getApiRouteId())) {
				if (beforeEntity.getPlateType() == PlateType.LARGE) {
					emptySeat = 45;
				} else {
					emptySeat = 70;
				}
			}
			var route = beforeEntity.getRoute();
			return BusRemainSeatEntity.build()
				.route(route)
				.station(station)
				.stationSeq(stationSeq)
				.emptySeat(emptySeat)
				.plateNo(beforeEntity.getPlateNo())
				.plateType(beforeEntity.getPlateType())
				.setCreateTime(beforeEntity.getCreatedAt().plusMinutes(3))
				.build();
		} else if (after.isPresent()) {
			var afterEntity = after.get();
			int emptySeat = afterEntity.getRemainSeatCount();
			// 기점에서는 모든 자리가 비어있음
			if (stationSeq == 1) {
				if (afterEntity.getPlateType() == PlateType.LARGE) {
					emptySeat = 45;
				} else {
					emptySeat = 70;
				}
			}
			var route = getRoute(afterEntity.getApiRouteId());
			return BusRemainSeatEntity.build()
				.route(route)
				.station(station)
				.stationSeq(stationSeq)
				.emptySeat(emptySeat)
				.plateNo(afterEntity.getPlateNo())
				.plateType(afterEntity.getPlateType())
				.setCreateTime(afterEntity.getCreatedAt().minusMinutes(3))
				.build();
		} else {
			throw new InvalidLocationNotFoundException();
		}
	}

	private BusRouteEntity getRoute(String apiRouteId) throws InvalidLocationNotFoundException {
		if (!routeMap.containsKey(apiRouteId)) {
			routeMap.put(apiRouteId, busRouteRepository.findByApiRouteId(apiRouteId));
		}
		return routeMap.get(apiRouteId).orElseThrow(InvalidLocationNotFoundException::new);
	}

	private BusStationEntity getStation(String apiStationId) throws InvalidLocationNotFoundException {
		if (!stationMap.containsKey(apiStationId)) {
			stationMap.put(apiStationId, busStationRepository.findByApiStationId(apiStationId));
		}
		return stationMap.get(apiStationId).orElseThrow(InvalidLocationNotFoundException::new);
	}

	private List<BusRouteStationEntity> getRouteStationByRouteId(String apiRouteId) {
		var routeId = getRoute(apiRouteId).getId();
		if (!routeStationMap.containsKey(routeId)) {
			var list = busRouteStationRepository.findAllByRouteId(routeId);
			list.sort(Comparator.comparingInt(BusRouteStationEntity::getStationSeq));
			routeStationMap.put(routeId, list);
		}
		return routeStationMap.get(routeId);
	}

	private Integer getRouteLength(String apiRouteId) throws InvalidLocationNotFoundException {
		if (!routeLengthMap.containsKey(apiRouteId)) {
			var route = getRoute(apiRouteId);
			routeLengthMap.put(apiRouteId, busRouteStationRepository.countAllByRoute(route));
		}
		return routeLengthMap.get(apiRouteId);
	}

	/**
	 * 주어진 두 시간 {@code time1}과 {@code time2} 사이의 구간을 주어진 개수 {@code num}만큼 나누어
	 * 그중 첫 번째 구간의 시작 시간을 반환합니다.
	 *
	 * @param time1 시작 시간으로 사용할 {@code LocalDateTime}입니다.
	 * @param time2 종료 시간으로 사용할 {@code LocalDateTime}입니다.
	 * @param num   두 시간 사이의 구간을 나눌 개수입니다.
	 * @return 첫 번째 구간의 끝 시간에 해당하는 {@code LocalDateTime} 객체를 반환합니다.
	 */
	private LocalDateTime getBetweenTime(LocalDateTime time1, LocalDateTime time2, int num) {
		Duration duration = Duration.between(time1, time2);
		Duration halfDuration = duration.dividedBy(num);
		return time1.plus(halfDuration);
	}
}
