package com.talkka.server.bus.service;

import static com.talkka.server.bus.BusTestFactory.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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

import com.talkka.server.bus.dao.BusRouteRepository;
import com.talkka.server.bus.dao.BusRouteStationEntity;
import com.talkka.server.bus.dao.BusRouteStationRepository;
import com.talkka.server.bus.dao.BusStationRepository;
import com.talkka.server.common.exception.http.BadRequestException;

@ExtendWith(MockitoExtension.class)
public class BusRouteStationServiceTest {

	@InjectMocks
	BusRouteStationService routeStationService;

	@Mock
	private BusRouteRepository routeRepository;
	@Mock
	private BusStationRepository stationRepository;
	@Mock
	private BusRouteStationRepository routeStationRepository;

	@Nested
	@DisplayName("createStation method")
	public class CreateStationTest {
		@Test
		void BusRouteStationReqDto를_요청으로_받아_BusRouteStationRepository에_저장한다() {
			/// given
			Long id = 1L;
			var createDto = getBusRouteStationCreateDto(id);
			var expected = getBusRouteStationRespDto(id, getBusRouteRespDto(id), getBusStationRespDto(id));
			given(routeRepository.findByApiRouteId(any(String.class))).willReturn(Optional.of(getBusRouteEntity(id)));
			given(stationRepository.findByApiStationId(any(String.class))).willReturn(
				Optional.of(getBusStationEntity(id)));
			given(routeStationRepository.save(any(BusRouteStationEntity.class))).willReturn(
				getBusRouteStationEntity(id, getBusRouteEntity(id), getBusStationEntity(id)));
			// when
			var result = routeStationService.createRouteStation(createDto);
			// then
			assertThat(result).isEqualTo(expected);
		}

		@Test
		void 존재하지_않는_Route면_Exception을_발생시킨다() {
			// given
			var createDto = getBusRouteStationCreateDto(1L);
			given(routeRepository.findByApiRouteId(any())).willReturn(Optional.empty());
			// when
			// then
			assertThatThrownBy(() -> routeStationService.createRouteStation(createDto)).isInstanceOf(
				BadRequestException.class).hasMessage("존재하지 않는 노선입니다.");
		}

		@Test
		void 존재하지_않는_Station이면_Exception을_발생시킨다() {
			// given
			var createDto = getBusRouteStationCreateDto(1L);
			given(routeRepository.findByApiRouteId(any())).willReturn(Optional.of(getBusRouteEntity(1L)));
			given(stationRepository.findByApiStationId(any())).willReturn(Optional.empty());
			// when
			// then
			assertThatThrownBy(() -> routeStationService.createRouteStation(createDto)).isInstanceOf(
				BadRequestException.class).hasMessage("존재하지 않는 정류장입니다.");
		}
	}

	@Nested
	@DisplayName("getRouteStationsByRouteIdAndStationId method")
	public class GetRouteStationsByRouteIdAndStationIdTest {
		@Test
		void RouteId와_StationId를_받아_해당_아이디의_노선정류장을_조회한다() {
			// given
			Long routeId = 1L;
			Long stationId = 1L;
			var expected = List.of(getBusRouteStationRespDto(1L, getBusRouteRespDto(1L), getBusStationRespDto(1L)));
			given(routeStationRepository.findAllByRouteIdAndStationId(anyLong(), anyLong())).willReturn(
				List.of(getBusRouteStationEntity(1L, getBusRouteEntity(1L), getBusStationEntity(1L))));
			// when
			var result = routeStationService.getRouteStationsByRouteIdAndStationId(routeId, stationId);
			// then
			assertThat(result).isEqualTo(expected);
			verify(routeStationRepository, times(1)).findAllByRouteIdAndStationId(anyLong(), anyLong());
		}
	}

	@Nested
	@DisplayName("getRouteStationsByRouteId method")
	public class GetRouteStationsByRouteIdTest {
		@Test
		void RouteId를_받아_해당_아이디의_노선정류장을_조회한다() {
			// given
			Long routeId = 1L;
			var expected = List.of(getBusRouteStationRespDto(1L, getBusRouteRespDto(1L), getBusStationRespDto(1L)));
			given(routeStationRepository.findAllByRouteId(anyLong())).willReturn(
				List.of(getBusRouteStationEntity(1L, getBusRouteEntity(1L), getBusStationEntity(1L))));
			// when
			var result = routeStationService.getRouteStationsByRouteId(routeId);
			// then
			assertThat(result).isEqualTo(expected);
			verify(routeStationRepository, times(1)).findAllByRouteId(anyLong());
		}
	}

	@Nested
	@DisplayName("getRouteStationsByStationId method")
	public class GetRouteStationsByStationIdTest {
		@Test
		void StationId를_받아_해당_아이디의_노선정류장을_조회한다() {
			// given
			Long stationId = 1L;
			var expected = List.of(getBusRouteStationRespDto(1L, getBusRouteRespDto(1L), getBusStationRespDto(1L)));
			given(routeStationRepository.findAllByStationId(anyLong())).willReturn(
				List.of(getBusRouteStationEntity(1L, getBusRouteEntity(1L), getBusStationEntity(1L))));
			// when
			var result = routeStationService.getRouteStationsByStationId(stationId);
			// then
			assertThat(result).isEqualTo(expected);
			verify(routeStationRepository, times(1)).findAllByStationId(anyLong());
		}
	}

	@Nested
	@DisplayName("getRouteStations method")
	public class GetRouteStationsTest {
		@Test
		void 모든_노선정류장을_조회한다() {
			// given
			var expected = List.of(getBusRouteStationRespDto(1L, getBusRouteRespDto(1L), getBusStationRespDto(1L)));
			given(routeStationRepository.findAll()).willReturn(
				List.of(getBusRouteStationEntity(1L, getBusRouteEntity(1L), getBusStationEntity(1L))));
			// when
			var result = routeStationService.getRouteStations();
			// then
			assertThat(result).isEqualTo(expected);
			verify(routeStationRepository, times(1)).findAll();
		}
	}

	@Nested
	@DisplayName("getRouteStationById method")
	public class GetRouteStationByIdTest {
		@Test
		void ID를_받아_해당_아이디의_노선정류장을_조회한다() {
			// given
			Long id = 1L;
			var expected = getBusRouteStationRespDto(id, getBusRouteRespDto(id), getBusStationRespDto(id));
			given(routeStationRepository.findById(anyLong())).willReturn(
				Optional.of(getBusRouteStationEntity(id, getBusRouteEntity(id), getBusStationEntity(id))));
			// when
			var result = routeStationService.getRouteStationById(id);
			// then
			assertThat(result).isEqualTo(expected);
			verify(routeStationRepository, times(1)).findById(anyLong());
		}

		@Test
		void ID가_존재하지_않으면_Exception을_throw한다() {
			// given
			Class<?> exceptionClass = BadRequestException.class;
			given(routeStationRepository.findById(anyLong())).willReturn(Optional.empty());
			// when
			// then
			assertThatThrownBy(
				() -> routeStationService.getRouteStationById(1L)
			).isInstanceOf(exceptionClass)
				.hasMessage("존재하지 않는 노선정류장입니다.");
			verify(routeStationRepository, times(1)).findById(anyLong());
		}
	}
}
