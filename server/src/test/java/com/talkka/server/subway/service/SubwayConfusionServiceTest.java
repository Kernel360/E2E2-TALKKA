package com.talkka.server.subway.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.talkka.server.common.enums.TimeSlot;
import com.talkka.server.common.exception.http.NotFoundException;
import com.talkka.server.common.util.EnumCodeConverterUtils;
import com.talkka.server.subway.dao.SubwayConfusionEntity;
import com.talkka.server.subway.dao.SubwayConfusionRepository;
import com.talkka.server.subway.dao.SubwayStationEntity;
import com.talkka.server.subway.dao.SubwayStationRepository;
import com.talkka.server.subway.dto.SubwayConfusionRespDto;
import com.talkka.server.subway.enums.DayType;
import com.talkka.server.subway.enums.Updown;

@ExtendWith(MockitoExtension.class)
public class SubwayConfusionServiceTest {
	@InjectMocks
	private SubwayConfusionService confusionService;

	@Mock
	private SubwayConfusionRepository confusionRepository;

	@Mock
	private SubwayStationRepository stationRepository;

	@Nested
	@DisplayName("getSubwayConfusion 메서드 테스트")
	public class getSubwayConfusion {

		Long stationId = 1L;
		String dayTypeCode = DayType.DAY.getCode();
		String updownCode = Updown.UP.getCode();
		String timeSlotCode = TimeSlot.T_11_00.getCode();

		DayType dayType = EnumCodeConverterUtils.fromCode(DayType.class, dayTypeCode);
		Updown updown = EnumCodeConverterUtils.fromCode(Updown.class, updownCode);
		TimeSlot timeSlot = EnumCodeConverterUtils.fromCode(TimeSlot.class, timeSlotCode);

		@Test
		void 사용자가_원하는_지하철_역의_ID와_요일과_방향과_시간대를_받아_혼잡도를_반환한다() {
			//given
			SubwayStationEntity subwayStationEntity = SubwayStationEntity.builder()
				.id(stationId)
				.build();

			SubwayConfusionEntity subwayConfusionEntity = SubwayConfusionEntity.builder()
				.subwayStation(subwayStationEntity)
				.dayType(dayType)
				.updown(updown)
				.timeSlot(timeSlot)
				.confusion(100.0)
				.build();

			given(stationRepository.existsById(stationId)).willReturn(true);
			given(confusionRepository.findBySubwayStationIdAndDayTypeAndUpdownAndTimeSlot(
				stationId, dayType, updown, timeSlot)).willReturn(Optional.of(subwayConfusionEntity));

			//when
			SubwayConfusionRespDto result = confusionService.getSubwayConfusion(
				stationId, dayTypeCode, updownCode, timeSlotCode);

			//then
			assertThat(result).isEqualTo(SubwayConfusionRespDto.of(subwayConfusionEntity));
		}

		@Test
		void 존재하지_않는_지하철역_ID일_경우_Exception을_throw한다() {
			//given
			Class<?> exceptionClass = NotFoundException.class; // 추후 변경될 가능성이 있어, 변수로 따로 지정함
			given(stationRepository.existsById(stationId)).willReturn(false);

			//when
			//then
			assertThatThrownBy(
				() -> confusionService.getSubwayConfusion(stationId, dayTypeCode, updownCode, timeSlotCode))
				.isInstanceOf(exceptionClass)
				.hasMessage("존재하지 않는 지하철 역입니다.");
		}

		@Test
		void 혼잡도가_조회되지_않는_경우_Exception을_throw한다() {
			//given
			Class<?> exceptionClass = NotFoundException.class; // 추후 변경될 가능성이 있어, 변수로 따로 지정함
			given(stationRepository.existsById(stationId)).willReturn(true);
			given(confusionRepository.findBySubwayStationIdAndDayTypeAndUpdownAndTimeSlot(
				stationId, dayType, updown, timeSlot)).willReturn(Optional.empty());

			//when
			//then
			assertThatThrownBy(
				() -> confusionService.getSubwayConfusion(stationId, dayTypeCode, updownCode, timeSlotCode))
				.isInstanceOf(exceptionClass)
				.hasMessage("조회 가능한 혼잡도 정보가 없습니다.");
		}
	}
}
