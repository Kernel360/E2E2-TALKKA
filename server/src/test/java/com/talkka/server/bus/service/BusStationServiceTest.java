package com.talkka.server.bus.service;

import static com.talkka.server.bus.BusTestFactory.*;
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

import com.talkka.server.bus.dao.BusStationEntity;
import com.talkka.server.bus.dao.BusStationRepository;
import com.talkka.server.bus.dto.BusStationCreateDto;
import com.talkka.server.bus.dto.BusStationRespDto;
import com.talkka.server.common.exception.http.BadRequestException;

@ExtendWith(MockitoExtension.class)
class BusStationServiceTest {
	@InjectMocks
	private BusStationService busStationService;

	@Mock
	private BusStationRepository busStationRepository;

	@Nested
	@DisplayName("getStationByStationId method")
	public class GetStationByStationId {
		@Test
		void ID를_기반으로_버스_정류장을_요청하면_레포지토리를_통해_조회하여_결과를_DTO로_반환한다() {
			Long stationId = 1L;
			BusStationEntity busStationEntity = getBusStationEntity(stationId);
			BusStationRespDto expected = getBusStationRespDto(stationId);
			given(busStationRepository.findById(stationId)).willReturn(Optional.of(busStationEntity));
			// when
			BusStationRespDto result = busStationService.getStationById(stationId);
			// then
			verify(busStationRepository, times(1)).findById(anyLong());
			assertThat(result).isEqualTo(expected);
		}

		@Test
		void ID가_존재하지_않으면_Exception을_throw한다() {
			// given
			Long stationId = 1L;
			Class<?> exceptionCLass = BadRequestException.class;
			given(busStationRepository.findById(anyLong())).willReturn(Optional.empty());
			// when
			// then
			assertThatThrownBy(
				() -> busStationService.getStationById(stationId)
			).isInstanceOf(exceptionCLass)
				.hasMessage("존재하지 않는 정거장입니다.");
			verify(busStationRepository, times(1)).findById(anyLong());
		}
	}

	@Nested
	@DisplayName("createBusStation method")
	public class CreateBusStationTest {
		@Test
		void BusStationReqDto를_요청으로_받아_BusStationRepository에_저장한다() {
			// given
			BusStationCreateDto createDto = getBusStationCreateDto(1L);
			given(busStationRepository.save(any(BusStationEntity.class))).willReturn(getBusStationEntity(1L));
			// when
			var result = busStationService.createStation(createDto);
			// then
			verify(busStationRepository, times(1)).save(any(BusStationEntity.class));
			assertThat(result).isEqualTo(getBusStationRespDto(1L));
		}

		@Test
		void 이미_등록된_정거장일_경우_Exception을_발생시킨다() {
			// given
			var createDto = getBusStationCreateDto(1L);
			Class<?> exceptionClass = BadRequestException.class;
			given(busStationRepository.existsByApiStationId(createDto.apiStationId())).willReturn(true);
			// when
			// then
			assertThatThrownBy(() -> busStationService.createStation(createDto))
				.isInstanceOf(exceptionClass).hasMessage("이미 등록된 정거장입니다.");
		}
	}

	@Nested
	@DisplayName("getStationsByStationName method")
	public class GetStationsByStationNameTest {
		@Test
		void 정류장_이름으로_요청하면_해당_이름으로_시작하는_정류장의_리스트를_반환한다() {
			// given
			String stationName = "정거장1";
			List<BusStationRespDto> expected = List.of(
				getBusStationRespDto(1L),
				getBusStationRespDto(12L)
			);
			List<BusStationEntity> entityList = List.of(
				getBusStationEntity(1L),
				getBusStationEntity(12L)
			);
			given(busStationRepository.findByStationNameLikeOrderByStationNameAsc(anyString())).willReturn(entityList);
			// when
			var result = busStationService.getStationsByStationName(stationName);
			// then
			assertThat(result).containsAll(expected);
			verify(busStationRepository, times(1)).findByStationNameLikeOrderByStationNameAsc(anyString());
		}
	}

	@Nested
	@DisplayName("getStations method")
	public class GetStationsTest {
		@Test
		void 모든_정류장을_조회한다() {
			// given
			List<BusStationRespDto> expected = List.of(
				getBusStationRespDto(1L),
				getBusStationRespDto(12L)
			);
			List<BusStationEntity> entityList = List.of(
				getBusStationEntity(1L),
				getBusStationEntity(12L)
			);
			given(busStationRepository.findAll()).willReturn(entityList);
			// when
			var result = busStationService.getStations();
			// then
			assertThat(result).containsAll(expected);
			verify(busStationRepository, times(1)).findAll();
		}
	}
}