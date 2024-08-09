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
	@DisplayName("createBusStation method")
	public class CreateBusStationTest {
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
			var result = routeStationService.createBusRouteStation(createDto);
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
			assertThatThrownBy(() -> routeStationService.createBusRouteStation(createDto)).isInstanceOf(
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
			assertThatThrownBy(() -> routeStationService.createBusRouteStation(createDto)).isInstanceOf(
				BadRequestException.class).hasMessage("존재하지 않는 정류장입니다.");
		}
	}

	@Nested
	@DisplayName("findById method")
	public class FindByIdTest {

		@Test
		void Id를_받아_해당되는_정류장노선_정보를_조회하고_결과를_RespDto로_반환한다() {
			// given
			Long id = 1L;
			var expected = getBusRouteStationRespDto(id, getBusRouteRespDto(id), getBusStationRespDto(id));
			given(routeStationRepository.findById(any(Long.class))).willReturn(
				Optional.of(getBusRouteStationEntity(id, getBusRouteEntity(id), getBusStationEntity(id))));
			// when
			var result = routeStationService.findById(id);
			// then
			assertThat(result).isEqualTo(expected);
		}

		@Test
		void 존재하지_않는_Id면_Exception을_throw한다() {
			// given
			Long id = 1L;
			given(routeStationRepository.findById(anyLong())).willReturn(Optional.empty());
			// when

			// then
			assertThatThrownBy(() -> routeStationService.findById(id)).isInstanceOf(BadRequestException.class)
				.hasMessage("존재하지 않는 노선정류장입니다.");
		}
	}

	@Nested
	@DisplayName("findByRouteId method")
	public class FindByRouteIdTest {

		@Test
		void RouteId를_받아_해당노선에_속하는_정류장정보를_조회하고_결과를_RespDto의_리스트로_반환한다() {
			// given
			Long routeId = 1L;
			Long id1 = 2L;
			Long id2 = 3L;
			Long id3 = 4L;
			var expected = List.of(
				getBusRouteStationRespDto(id1, getBusRouteRespDto(routeId), getBusStationRespDto(id1)),
				getBusRouteStationRespDto(id2, getBusRouteRespDto(routeId), getBusStationRespDto(id2)),
				getBusRouteStationRespDto(id3, getBusRouteRespDto(routeId), getBusStationRespDto(id3))
			);
			given(routeStationRepository.findByRouteId(any(Long.class))).willReturn(
				List.of(
					getBusRouteStationEntity(id1, getBusRouteEntity(routeId), getBusStationEntity(id1)),
					getBusRouteStationEntity(id2, getBusRouteEntity(routeId), getBusStationEntity(id2)),
					getBusRouteStationEntity(id3, getBusRouteEntity(routeId), getBusStationEntity(id3))
				)
			);
			// when
			var result = routeStationService.findByRouteId(routeId);
			// then
			assertThat(result).isEqualTo(expected);
		}
	}

	@Nested
	@DisplayName("findByStationId method")
	public class FindByStationIdTest {

		@Test
		void StationId를_받아_해당노선에_속하는_정류장정보를_조회하고_결과를_RespDto의_리스트로_반환한다() {
			// given
			Long stationId = 1L;
			Long id1 = 2L;
			Long id2 = 3L;
			Long id3 = 4L;
			var expected = List.of(
				getBusRouteStationRespDto(id1, getBusRouteRespDto(id1), getBusStationRespDto(stationId)),
				getBusRouteStationRespDto(id2, getBusRouteRespDto(id2), getBusStationRespDto(stationId)),
				getBusRouteStationRespDto(id3, getBusRouteRespDto(id3), getBusStationRespDto(stationId))
			);
			given(routeStationRepository.findByStationId(any(Long.class))).willReturn(
				List.of(
					getBusRouteStationEntity(id1, getBusRouteEntity(id1), getBusStationEntity(stationId)),
					getBusRouteStationEntity(id2, getBusRouteEntity(id2), getBusStationEntity(stationId)),
					getBusRouteStationEntity(id3, getBusRouteEntity(id3), getBusStationEntity(stationId))
				)
			);
			// when
			var result = routeStationService.findByStationId(stationId);
			// then
			assertThat(result).isEqualTo(expected);
		}
	}
}
