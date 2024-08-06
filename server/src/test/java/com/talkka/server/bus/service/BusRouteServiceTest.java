package com.talkka.server.bus.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.talkka.server.bus.dao.BusRouteEntity;
import com.talkka.server.bus.dao.BusRouteRepository;
import com.talkka.server.bus.dto.BusRouteCreateDto;
import com.talkka.server.bus.dto.BusRouteRespDto;
import com.talkka.server.bus.enums.BusRouteType;
import com.talkka.server.bus.enums.DistrictCode;
import com.talkka.server.common.exception.http.BadRequestException;

@ExtendWith(MockitoExtension.class)
public class BusRouteServiceTest {

	@InjectMocks
	private BusRouteService busRouteService;

	@Mock
	private BusRouteRepository busRouteRepository;

	private BusRouteCreateDto getBusRouteReqDto(String id) {

		return BusRouteCreateDto.builder()
			.apiRouteId(id)
			.routeName("7800" + id)
			.routeTypeCd(BusRouteType.DIRECT_SEAT_CITY_BUS)
			.routeTypeName(BusRouteType.DIRECT_SEAT_CITY_BUS.getName())
			.companyId("COMP123")
			.companyName("수형운수")
			.companyTel("02-123-4567")
			.districtCd(DistrictCode.DONGDUCHEON)
			.upFirstTime("05:30")
			.upLastTime("23:00")
			.downFirstTime("06:00")
			.downLastTime("00:35")
			.startMobileNo("101")
			.startStationId(1001L)
			.startStationName("기점 정류소")
			.endStationId(2002L)
			.endMobileNo("202")
			.endStationName("종점 정류소")
			.regionName("서울")
			.peekAlloc(15)
			.nPeekAlloc(25)
			.build();
	}

	private BusRouteRespDto getBusRouteRespDto(Long id) {
		return BusRouteRespDto.builder()
			.routeId(id)
			.routeName("7800" + id)
			.routeTypeCd(BusRouteType.DIRECT_SEAT_CITY_BUS)
			.routeTypeName(BusRouteType.DIRECT_SEAT_CITY_BUS.getName())
			.districtCd(DistrictCode.DONGDUCHEON)
			.upFirstTime("05:30")
			.upLastTime("23:00")
			.downFirstTime("06:00")
			.downLastTime("00:35")
			.startMobileNo("101")
			.startStationId(1001L)
			.startStationName("기점 정류소")
			.endStationId(2002L)
			.endMobileNo("202")
			.endStationName("종점 정류소")
			.regionName("서울")
			.peekAlloc(15)
			.nPeekAlloc(25)
			.build();
	}

	@Nested
	@DisplayName("createBusRoute method")
	public class CreateBusRouteTest {
		@Test
		void 버스_노선을_생성한다() {
			// given
			BusRouteCreateDto busRouteCreateDto = getBusRouteReqDto("1");
			BusRouteRespDto busRouteRespDto = getBusRouteRespDto(1L);
			BusRouteEntity busRouteEntity = BusRouteEntity.builder()
				.id(1L)
				.apiRouteId(busRouteCreateDto.getApiRouteId())
				.routeName(busRouteCreateDto.getRouteName())
				.routeTypeCd(BusRouteType.DIRECT_SEAT_CITY_BUS)
				.routeTypeName(BusRouteType.DIRECT_SEAT_CITY_BUS.getName())
				.companyId("COMP123")
				.companyName("수형운수")
				.companyTel("02-123-4567")
				.districtCd(DistrictCode.DONGDUCHEON)
				.upFirstTime("05:30")
				.upLastTime("23:00")
				.downFirstTime("06:00")
				.downLastTime("00:35")
				.startMobileNo("101")
				.startStationId(1001L)
				.startStationName("기점 정류소")
				.endStationId(2002L)
				.endMobileNo("202")
				.endStationName("종점 정류소")
				.regionName("서울")
				.peekAlloc(15)
				.nPeekAlloc(25)
				.build();
			given(busRouteRepository.save(any(BusRouteEntity.class))).willReturn(busRouteEntity);
			given(busRouteRepository.existsByApiRouteId(any(String.class))).willReturn(true);
			// when
			BusRouteRespDto result = busRouteService.createBusRoute(busRouteCreateDto);
			// then
			assertThat(result).isEqualTo(busRouteRespDto);
		}

		@Test
		void 존재하는_버스_노선일_경우_Exception을_발생시킨다() {
			// given
			BusRouteCreateDto reqDto = getBusRouteReqDto("1");
			given(busRouteRepository.existsByApiRouteId(any(String.class))).willReturn(false);
			// when
			// then
			assertThatThrownBy(
				() -> busRouteService.createBusRoute(reqDto)
			).isInstanceOf(BadRequestException.class)
				.hasMessage("이미 등록된 버스 노선입니다.");
		}
	}
}
