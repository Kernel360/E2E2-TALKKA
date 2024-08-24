package com.talkka.server.bus.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talkka.server.bus.dao.BusLocationEntity;
import com.talkka.server.bus.dao.BusLocationRepository;
import com.talkka.server.bus.dao.BusRouteEntity;
import com.talkka.server.bus.dao.BusRouteRepository;
import com.talkka.server.bus.dao.BusStatEntity;
import com.talkka.server.bus.dao.BusStatRepository;
import com.talkka.server.bus.dao.BusStationEntity;
import com.talkka.server.bus.dao.BusStationRepository;
import com.talkka.server.bus.dto.BusStatReqDto;
import com.talkka.server.bus.dto.BusStatRespDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusStatService {
	private final BusRouteRepository busRouteRepository;
	private final BusStationRepository busStationRepository;
	private final BusLocationRepository busLocationRepository;
	private final BusStatRepository busStatRepository;

	public List<BusStatRespDto> getBusStat(BusStatReqDto busStatReqDto) {
		return busStatRepository.findByRouteIdAndStationIdAndBeforeTimeBetween(
				busStatReqDto.routeId(),
				busStatReqDto.stationId(),
				busStatReqDto.startDateTime(),
				busStatReqDto.endDateTime()
			).stream()
			.map(BusStatRespDto::of)
			.toList();
	}

	public List<BusStatRespDto> getBusStatNow(Long routeId, Long stationId) {
		LocalDateTime now = LocalDateTime.now();
		int startTime = getTime(now.minusMinutes(30));
		int endTime = getTime(now.plusMinutes(30));
		return busStatRepository.findByRouteIdAndStationIdAndDayOfWeekBetweenNow(
				routeId,
				stationId,
				now.getDayOfWeek().getValue(),
				startTime,
				endTime
			).stream()
			.map(BusStatRespDto::of)
			.toList();
	}

	// 생성된 위치정보 중 start~end 기간에 있는 것들만 가공
	@Transactional
	public void makeStatDataBetween(LocalDateTime start, LocalDateTime end) {
		List<BusLocationEntity> locationList = busLocationRepository.findByCreatedAtBetween(start, end);
		Map<String, BusLocationEntity> locationMap = new HashMap<>();
		Map<String, BusRouteEntity> routeMap = new HashMap<>();
		Map<String, BusStationEntity> stationMap = new HashMap<>();
		busRouteRepository.findAll().forEach(route -> routeMap.put(route.getApiRouteId(), route));
		busStationRepository.findAll().forEach(station -> stationMap.put(station.getApiStationId(), station));
		// 가공하려는 기간에 속하는 통계정보를 가져와 엔티티의 해시코드를 set 에 저장
		Set<Integer> statIdentifierSet = new HashSet<>();
		busStatRepository.findByBeforeTimeBetween(start, end).forEach(stat -> statIdentifierSet.add(stat.identifier()));

		for (BusLocationEntity afterLocation : locationList) {
			// 같은 버스의 위치정보가 map 에 없으면 넣고 넘김
			if (!locationMap.containsKey(afterLocation.getPlateNo())) {
				locationMap.put(afterLocation.getPlateNo(), afterLocation);
			} else {
				// 같은 버스의 직전 위치를 map 에서 가져옴
				BusLocationEntity beforeLocation = locationMap.get(afterLocation.getPlateNo());

				if (// 위치정보들 간 시간 차이가 1시간 미만이어야함
					Duration.between(beforeLocation.getCreatedAt(), afterLocation.getCreatedAt()).toHours() < 1
						// 위치정보들 간 정거장 차이가 1정거장 이하여야함
						&& afterLocation.getStationSeq() - beforeLocation.getStationSeq() <= 1
						// 위치정보들 둘다 좌석정보를 가지고 있어야함
						&& beforeLocation.getRemainSeatCount() != -1 && afterLocation.getRemainSeatCount() != -1
				) {
					BusRouteEntity route = routeMap.get(beforeLocation.getApiRouteId());
					BusStationEntity station = stationMap.get(beforeLocation.getApiStationId());
					BusStatEntity statEntity = toBusStatEntity(beforeLocation, afterLocation, route, station);
					// 이미 존재하는 통계 정보인지 확인한 후 db에 저장
					if (!statIdentifierSet.contains(statEntity.identifier())) {
						busStatRepository.save(statEntity);
					}
				}
				// 위치정보 갱신
				locationMap.put(afterLocation.getPlateNo(), afterLocation);
			}
		}
	}

	// 두개의 위치정보를 가지고 BusStatEntity 를 생성
	private static BusStatEntity toBusStatEntity(BusLocationEntity before, BusLocationEntity after,
		BusRouteEntity route, BusStationEntity station) {
		return BusStatEntity.builder()
			.route(route)
			.station(station)
			.apiRouteId(after.getApiRouteId())
			.apiStationId(before.getApiStationId())
			.beforeSeat(before.getRemainSeatCount().intValue())
			.afterSeat(after.getRemainSeatCount().intValue())
			.seatDiff(after.getRemainSeatCount() - before.getRemainSeatCount())
			.beforeTime(before.getCreatedAt())
			.afterTime(after.getCreatedAt())
			.plateNo(after.getPlateNo())
			.plateType(after.getPlateType())
			.dayOfWeek(before.getCreatedAt().getDayOfWeek().getValue())
			.time(getTime(before.getCreatedAt()))
			.build();
	}

	// 시간, 분을 이어붙인 int 값 ex) 23:59 -> 2359, 08:27->827
	private static int getTime(LocalDateTime localDateTime) {
		return localDateTime.getHour() * 100 + localDateTime.getMinute();
	}

	// old logic
	// @Deprecated
	// @Transactional
	// public void process() {
	// 	List<Integer> apiCallNoList = busLocationRepository.getDistinctApiCallNoList();
	// 	apiCallNoList.sort(Comparator.naturalOrder());
	// 	Map<String, BusLocationEntity> prev = new HashMap<>();
	// 	System.out.println(apiCallNoList);
	// 	for (Integer apiCallNo : apiCallNoList) {
	// 		Map<String, BusLocationEntity> cur = new HashMap<>();
	// 		List<BusLocationEntity> locationList = busLocationRepository.findByApiCallNo(apiCallNo);
	// 		for (BusLocationEntity location : locationList) {
	// 			BusLocationEntity beforeLocation;
	// 			if ((beforeLocation = prev.get(location.getPlateNo())) != null
	// 				// 두정거장 이상 넘어가면 체크하지 않음
	// 				&& location.getStationSeq() - beforeLocation.getStationSeq() <= 1 && (
	// 				location.getRemainSeatCount() != -1 && beforeLocation.getRemainSeatCount() != -1)) {
	// 				BusStatEntity statEntity = toBusStatEntity(beforeLocation, location);
	// 				busStatRepository.save(statEntity);
	// 			}
	// 			cur.put(location.getPlateNo(), location);
	// 		}
	// 		prev = cur;
	// 	}
	// }
}
