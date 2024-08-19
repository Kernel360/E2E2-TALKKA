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

import com.talkka.server.common.enums.TimeSlot;
import com.talkka.server.subway.dao.SubwayConfusionEntity;
import com.talkka.server.subway.dao.SubwayConfusionRepository;
import com.talkka.server.subway.dao.SubwayStationEntity;
import com.talkka.server.subway.dao.SubwayStationRepository;
import com.talkka.server.subway.dto.SubwayConfusionRespDto;
import com.talkka.server.subway.enums.DayType;
import com.talkka.server.subway.enums.Line;
import com.talkka.server.subway.enums.Updown;
import com.talkka.server.subway.exception.ConfusionNotFoundException;
import com.talkka.server.subway.exception.StationNotFoundException;

@ExtendWith(MockitoExtension.class)
public class SubwayConfusionServiceTest {
	@InjectMocks
	private SubwayConfusionService confusionService;

	@Mock
	private SubwayConfusionRepository confusionRepository;

	@Mock
	private SubwayStationRepository stationRepository;

	private final Long stationId = 1L;
	private final String dayType = DayType.DAY.toString();
	private final String updown = Updown.UP.toString();

	private SubwayStationEntity getStationEntity(Long stationId) {
		return SubwayStationEntity.builder()
			.id(stationId)
			.stationCode("0150")
			.stationName("서울역")
			.line(Line.LINE_ONE)
			.build();
	}

	private SubwayConfusionEntity getConfusionEntity(Long stationId) {
		return SubwayConfusionEntity.builder()
			.id(1L)
			.subwayStation(getStationEntity(stationId))
			.stationCode("0150")
			.stationName("서울역")
			.line(Line.LINE_ONE)
			.dayType(DayType.DAY)
			.updown(Updown.UP)
			.timeSlot(TimeSlot.T_00_00)
			.confusion(100.0)
			.build();
	}

	@Nested
	@DisplayName("getConfusion 메서드 테스트")
	public class getConfusion {
		private final String timeSlot = TimeSlot.T_11_00.toString();

		@Test
		void 사용자가_원하는_지하철_역의_ID와_요일과_방향과_시간대를_받아_혼잡도를_반환한다() {
			//given
			SubwayConfusionEntity entity = getConfusionEntity(stationId);

			given(stationRepository.existsById(stationId)).willReturn(true);
			given(confusionRepository.findBySubwayStationIdAndDayTypeAndUpdownAndTimeSlot(
				stationId,
				DayType.valueOfEnumString(dayType),
				Updown.valueOfEnumString(updown),
				TimeSlot.valueOfEnumString(timeSlot))
			).willReturn(Optional.of(entity));

			//when
			SubwayConfusionRespDto result = confusionService.getConfusion(
				stationId, dayType, updown, timeSlot);

			//then
			verify(stationRepository, times(1)).existsById(stationId);
			verify(confusionRepository, times(1))
				.findBySubwayStationIdAndDayTypeAndUpdownAndTimeSlot(
					stationId,
					DayType.valueOfEnumString(dayType),
					Updown.valueOfEnumString(updown),
					TimeSlot.valueOfEnumString(timeSlot)
				);
			assertThat(result).isEqualTo(SubwayConfusionRespDto.of(entity));
		}

		@Test
		void 존재하지_않는_지하철역_ID일_경우_Exception을_throw한다() {
			//given
			given(stationRepository.existsById(stationId)).willReturn(false);

			//when
			//then
			assertThatThrownBy(
				() -> confusionService.getConfusion(stationId, dayType, updown, timeSlot))
				.isInstanceOf(StationNotFoundException.class)
				.hasMessage("존재하지 않는 지하철 역입니다. StationId: " + stationId);
		}

		@Test
		void 혼잡도가_조회되지_않는_경우_Exception을_throw한다() {
			//given
			given(stationRepository.existsById(stationId)).willReturn(true);
			given(confusionRepository.findBySubwayStationIdAndDayTypeAndUpdownAndTimeSlot(
				stationId,
				DayType.valueOfEnumString(dayType),
				Updown.valueOfEnumString(updown),
				TimeSlot.valueOfEnumString(timeSlot)))
				.willReturn(Optional.empty());

			//when
			//then
			assertThatThrownBy(
				() -> confusionService.getConfusion(stationId, dayType, updown, timeSlot))
				.isInstanceOf(ConfusionNotFoundException.class)
				.hasMessage("조회 가능한 혼잡도 정보가 없습니다.");
		}
	}

	@Nested
	@DisplayName("getConfusionList 메서드 테스트")
	public class getConfusionList {
		private final String startTimeSlotCode = TimeSlot.T_08_00.toString();
		private final String endTimeSlotCode = TimeSlot.T_09_00.toString();

		@Test
		void 사용자가_원하는_지하철_역의_ID와_요일과_방향과_시간대들을_받아_혼잡도들을_반환한다() {
			//given
			List<SubwayConfusionEntity> entityList = List.of(
				getConfusionEntity(stationId),
				getConfusionEntity(stationId)
			);

			List<SubwayConfusionRespDto> dtoList = entityList.stream()
				.map(SubwayConfusionRespDto::of)
				.toList();

			given(stationRepository.existsById(stationId)).willReturn(true);
			given(confusionRepository.findBySubwayStationIdAndDayTypeAndUpdownAndTimeSlotBetween(
				stationId,
				DayType.valueOfEnumString(dayType),
				Updown.valueOfEnumString(updown),
				TimeSlot.valueOfEnumString(startTimeSlotCode),
				TimeSlot.valueOfEnumString(endTimeSlotCode))
			).willReturn(entityList);

			//when
			List<SubwayConfusionRespDto> result = confusionService.getConfusionList(
				stationId, dayType, updown, startTimeSlotCode, endTimeSlotCode);

			//then
			verify(stationRepository, times(1)).existsById(stationId);
			verify(confusionRepository, times(1))
				.findBySubwayStationIdAndDayTypeAndUpdownAndTimeSlotBetween(
					stationId,
					DayType.valueOfEnumString(dayType),
					Updown.valueOfEnumString(updown),
					TimeSlot.valueOfEnumString(startTimeSlotCode),
					TimeSlot.valueOfEnumString(endTimeSlotCode)
				);
			assertThat(result).isEqualTo(dtoList);
		}

		@Test
		void 존재하지_않는_지하철역_ID일_경우_Exception을_throw한다() {
			//given
			given(stationRepository.existsById(stationId)).willReturn(false);

			//when
			//then
			assertThatThrownBy(
				() -> confusionService.getConfusionList(stationId, dayType, updown, startTimeSlotCode, endTimeSlotCode))
				.isInstanceOf(StationNotFoundException.class)
				.hasMessage("존재하지 않는 지하철 역입니다. StationId: " + stationId);
		}
	}
}
