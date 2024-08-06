package com.talkka.server.bus.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.math.BigDecimal;
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
import com.talkka.server.bus.enums.CenterStation;
import com.talkka.server.bus.enums.DistrictCode;
import com.talkka.server.bus.enums.TurnStation;
import com.talkka.server.common.exception.http.BadRequestException;

@ExtendWith(MockitoExtension.class)
class BusStationServiceTest {
	@InjectMocks
	private BusStationService busStationService;

	@Mock
	private BusStationRepository busStationRepository;

	private BusStationEntity getBusStationEntity(Long id) {
		return BusStationEntity.builder()
			.id(id)
			.apiStationId("STN" + id)
			.stationName("정거장" + id)
			.regionName("서울")
			.districtCd(DistrictCode.DONGDUCHEON)
			.centerYn(CenterStation.CENTER_STATION)
			.turnYn(TurnStation.TURN_STATION)
			.longitude(BigDecimal.valueOf(127.123456))
			.latitude(BigDecimal.valueOf(37.123456))
			.build();
	}

	private BusStationCreateDto getBusStationCreateDto(Long id) {
		return BusStationCreateDto.builder()
			.apiStationId("STN" + id)
			.stationName("정거장" + id)
			.regionName("서울")
			.districtCd(DistrictCode.DONGDUCHEON)
			.centerYn(CenterStation.CENTER_STATION)
			.turnYn(TurnStation.TURN_STATION)
			.longitude(BigDecimal.valueOf(127.123456))
			.latitude(BigDecimal.valueOf(37.123456))
			.build();
	}

	private BusStationRespDto getBusStationRespDto(Long id) {
		return BusStationRespDto.builder()
			.stationId(id)
			.stationName("정거장" + id)
			.regionName("서울")
			.districtCd(DistrictCode.DONGDUCHEON)
			.centerYn(CenterStation.CENTER_STATION)
			.turnYn(TurnStation.TURN_STATION)
			.longitude(BigDecimal.valueOf(127.123456))
			.latitude(BigDecimal.valueOf(37.123456))
			.build();
	}

	@Nested
	@DisplayName("findByStationId method")
	public class FindByStationId {
		@Test
		void ID를_기반으로_버스_정류장을_요청하면_레포지토리를_통해_조회하여_결과를_DTO로_반환한다() {
			// given
			Long stationId = 1L;
			BusStationEntity busStationEntity = getBusStationEntity(stationId);
			BusStationRespDto expected = getBusStationRespDto(stationId);
			given(busStationRepository.findById(stationId)).willReturn(Optional.of(busStationEntity));
			// when
			BusStationRespDto result = busStationService.findByStationId(stationId);
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
				() -> busStationService.findByStationId(stationId)
			).isInstanceOf(exceptionCLass)
				.hasMessage("존재하지 않는 정거장입니다.");
			verify(busStationRepository, times(1)).findById(anyLong());
		}
	}
}