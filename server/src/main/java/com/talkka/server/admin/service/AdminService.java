package com.talkka.server.admin.service;

import java.util.ArrayList;
import java.util.List;

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
		// List<Object[]>로 쿼리 결과를 받음
		List<Object[]> results = bookmarkDetailRepository.countGroupedByBusRouteStationId();

		List<BookmarkStatRespDto> result = new ArrayList<>();

		// 결과를 순회하며 처리
		for (Object[] row : results) {
			Long key = (Long)row[0];  // routeStation.id
			Integer count = ((Number)row[1]).intValue();  // COUNT(b)를 Integer로 변환
			var busRouteStation = busRouteStationRepository.findById(key);
			if (busRouteStation.isEmpty()) {
				log.warn("등록되지 않은 노선-정거장 입니다 : {}", key);
				continue;
			}
			result.add(
				new BookmarkStatRespDto(
					BusRouteStationRespDto.of(busRouteStation.get()),
					count
				)
			);
		}

		// count로 오름차순 정렬
		result.sort((r1, r2) -> r2.count() - r1.count());
		return result;
	}

	public List<BusReviewStatRespDto> getBusReviewStats() {
		// List<Object[]>로 쿼리 결과를 받음
		List<Object[]> results = busReviewRepository.countGroupedByBusRouteStationId();
		List<BusReviewStatRespDto> result = new ArrayList<>();

		// 결과를 순회하며 처리
		for (Object[] row : results) {
			Long key = (Long)row[0];  // routeStation.id
			Integer count = ((Number)row[1]).intValue();  // COUNT(b)를 Integer로 변환
			var busRouteStation = busRouteStationRepository.findById(key);
			if (busRouteStation.isEmpty()) {
				log.warn("등록되지 않은 노선-정거장 입니다 : {}", key);
				continue;
			}
			result.add(
				new BusReviewStatRespDto(
					BusRouteStationRespDto.of(busRouteStation.get()),
					count
				)
			);
		}

		// count로 오름차순 정렬
		result.sort((r1, r2) -> r2.count() - r1.count());
		return result;
	}
}
