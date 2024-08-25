package com.talkka.server.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.talkka.server.admin.dto.BookmarkStatRespDto;
import com.talkka.server.admin.dto.BusReviewStatRespDto;
import com.talkka.server.bookmark.dao.BookmarkDetailRepository;
import com.talkka.server.bus.dao.BusRouteStationRepository;
import com.talkka.server.bus.dto.BusRouteStationRespDto;
import com.talkka.server.bus.exception.BusRouteStationNotFoundException;
import com.talkka.server.review.dao.BusReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

	private final BookmarkDetailRepository bookmarkDetailRepository;
	private final BusReviewRepository busReviewRepository;
	private final BusRouteStationRepository busRouteStationRepository;

	public List<BookmarkStatRespDto> getBookmarkStats() throws BusRouteStationNotFoundException {
		Map<Long, Integer> map = bookmarkDetailRepository.countGroupedByBusRouteStationId();
		List<BookmarkStatRespDto> result = new ArrayList<>();
		for (Long key : map.keySet()) {
			var busRouteStation = busRouteStationRepository.findById(key);
			if (busRouteStation.isEmpty()) {
				log.warn("등록되지 않은 노선-정거장 입니다 : {}", key);
				continue;
			}
			result.add(
				new BookmarkStatRespDto(
					BusRouteStationRespDto.of(busRouteStation.get()),
					map.get(key)
				)
			);
		}
		result.sort((r1, r2) -> r2.count() - r1.count()); // count 로 오름차순 정렬
		return result;
	}

	public List<BusReviewStatRespDto> getBusReviewStats() {
		Map<Long, Integer> map = busReviewRepository.countGroupedByBusRouteStationId();
		List<BusReviewStatRespDto> result = new ArrayList<>();
		for (Long key : map.keySet()) {
			var busRouteStation = busRouteStationRepository.findById(key);
			if (busRouteStation.isEmpty()) {
				log.warn("등록되지 않은 노선-정거장 입니다 : {}", key);
				continue;
			}
			result.add(
				new BusReviewStatRespDto(
					BusRouteStationRespDto.of(busRouteStation.get()),
					map.get(key)
				)
			);
		}
		result.sort((r1, r2) -> r2.count() - r1.count()); // count 로 오름차순 정렬
		return result;
	}
}
