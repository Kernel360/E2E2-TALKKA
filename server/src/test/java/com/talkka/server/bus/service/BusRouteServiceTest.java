package com.talkka.server.bus.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

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

	private BusRouteEntity getBusRouteEntity(Long id) {
		return BusRouteEntity.builder()
			.id(id)
			.apiRouteId("" + id)
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

	@Nested
	@DisplayName("findByRouteId method")
	public class FindByRouteIdTest {
		@Test
		void ID를_기반으로_버스_노선을_요청하면_레포지토리를_통해_결과를_DTO로_반환한다() {
			// given
			Long routeId = 1L;
			BusRouteEntity foundEntity = getBusRouteEntity(routeId);
			given(busRouteRepository.findById(anyLong())).willReturn(Optional.of(foundEntity));
			// when
			var BusRouteRespDto = busRouteService.findByRouteId(routeId);
			// then
			verify(busRouteRepository).findById(anyLong());
			assertThat(BusRouteRespDto).isEqualTo(getBusRouteRespDto(routeId));
		}

		@Test
		void ID가_존재하지_않으면_Exception을_throw한다() {
			// given
			Class<?> exceptionClass = BadRequestException.class;
			given(busRouteRepository.findById(anyLong())).willReturn(Optional.empty());
			// when
			// then
			assertThatThrownBy(
				() -> busRouteService.findByRouteId(1L)
			).isInstanceOf(exceptionClass)
				.hasMessage("존재하지 않는 노선입니다.");
			verify(busRouteRepository, times(1)).findById(anyLong());
		}
	}

	@Nested
	@DisplayName("findByRouteName method")
	public class FindByRouteNameTest {

		@Test
		void 버스노선이름을_요청으로_받아_repository에서_조회하고_해당_이름으로_시작하는_노선들을_리스트로_반환한다() {

			// given
			String routeName = "7800";
			var entityList = List.of(getBusRouteEntity(1L), getBusRouteEntity(2L));
			var expectedList = List.of(getBusRouteRespDto(1L), getBusRouteRespDto(2L));
			given(busRouteRepository.findByRouteNameLikeOrderByRouteNameAsc(any(String.class))).willReturn(entityList);

			// when
			var resultList = busRouteService.findByRouteName(routeName);

			// then
			verify(busRouteRepository, times(1)).findByRouteNameLikeOrderByRouteNameAsc(anyString());
			assertThat(resultList).containsAll(expectedList);

		}
	}
}
