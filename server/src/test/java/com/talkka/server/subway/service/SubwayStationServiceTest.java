package com.talkka.server.subway.service;

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

import com.talkka.server.common.exception.http.BadRequestException;
import com.talkka.server.subway.dao.SubwayStationEntity;
import com.talkka.server.subway.dao.SubwayStationRepository;
import com.talkka.server.subway.dto.SubwayStationReqDto;
import com.talkka.server.subway.dto.SubwayStationRespDto;
import com.talkka.server.subway.enums.Line;

@ExtendWith(MockitoExtension.class)
public class SubwayStationServiceTest {

	@InjectMocks
	SubwayStationService stationService;

	@Mock
	SubwayStationRepository stationRepository;

	private SubwayStationRespDto stationDtoFixture(Long stationId) {
		return SubwayStationRespDto.builder()
			.stationId(stationId)
			.stationName("서울역")
			.stationCode("0150")
			.lineCode(Line.LINE_ONE.getCode())
			.build();
	}

	private SubwayStationEntity stationEntityFixture(Long stationId) {
		return SubwayStationEntity.builder()
			.id(stationId)
			.stationName("서울역")
			.stationCode("0150")
			.line(Line.LINE_ONE)
			.build();
	}

	@Nested
	@DisplayName("getStation 메서드 테스트")
	public class getStation {

		@Test
		void 지하철_역의_ID를_받아_정보를_반환한다() {
			//given
			Long stationId = 1L;

			SubwayStationEntity subwayStationEntity = SubwayStationEntity.builder()
				.id(stationId)
				.stationName("서울역")
				.stationCode("0150")
				.line(Line.LINE_ONE)
				.build();

			given(stationRepository.findById(anyLong())).willReturn(Optional.of(subwayStationEntity));

			//when
			SubwayStationRespDto result = stationService.findByStationId(stationId);

			//then
			assertThat(result).isEqualTo(stationDtoFixture(stationId));
		}

		@Test
		void 올바르지_않은_지하철_역_ID를_넘겨받은_경우_Exception을_throw_한다() {
			//given
			Class<?> exceptionClass = BadRequestException.class; // 추후 변경될 가능성이 있어, 변수로 따로 지정함
			given(stationRepository.findById(anyLong())).willReturn(Optional.empty());

			//when
			//then
			assertThatThrownBy(() -> stationService.findByStationId(anyLong()))
				.isInstanceOf(exceptionClass)
				.hasMessage("존재하지 않는 역입니다.");
		}
	}

	@Nested
	@DisplayName("findByStationName 메서드 테스트")
	public class findByStationName {

		@Test
		void 지하철_역의_이름을_받아_해당하는_역의_리스트를_반환한다() {
			//given
			String stationName = "서울";
			List<SubwayStationEntity> subwayStationEntities = List.of(
				SubwayStationEntity.builder()
					.stationName("서울역")
					.line(Line.LINE_ONE)
					.build(),
				SubwayStationEntity.builder()
					.stationName("서울대입구")
					.line(Line.LINE_ONE)
					.build()
			);
			List<SubwayStationRespDto> expectedDtoList = List.of(
				SubwayStationRespDto.of(subwayStationEntities.get(0)),
				SubwayStationRespDto.of(subwayStationEntities.get(1))
			);

			given(stationRepository.findByStationNameLikeOrderByStationNameAsc(anyString())).willReturn(
				subwayStationEntities);

			//when
			List<SubwayStationRespDto> result = stationService.findByStationName(stationName);

			//then
			assertThat(result).isEqualTo(expectedDtoList);
		}
	}

	@Nested
	@DisplayName("saveStation 메서드 테스트")
	public class saveStation {

		SubwayStationReqDto subwayStationReqDto = SubwayStationReqDto.builder()
			.stationName("서울역")
			.stationCode("0150")
			.lineId(Line.LINE_ONE.getCode())
			.build();

		@Test
		void 제한된_요청에_따라_지하철_역을_생성한다() {
			//given
			Long stationId = 1L;

			given(stationRepository.save(any(SubwayStationEntity.class))).willReturn(
				stationEntityFixture(stationId));

			//when
			SubwayStationRespDto result = stationService.createStation(subwayStationReqDto);

			//then
			assertThat(result).isEqualTo(stationDtoFixture(stationId));
		}

		@Test
		void 이미_존재하는_지하철_역_ID라면_Exception을_throw한다() {
			//given
			Class<?> exceptionClass = BadRequestException.class; // 추후 변경될 가능성이 있어, 변수로 따로 지정함

			given(stationRepository.existsByStationCode(subwayStationReqDto.stationCode())).willReturn(true);

			//when
			//then
			assertThatThrownBy(() -> stationService.createStation(subwayStationReqDto))
				.isInstanceOf(exceptionClass)
				.hasMessage("이미 존재하는 지하철 역입니다");
		}
	}
}
