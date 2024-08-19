package com.talkka.server.subway.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.talkka.server.subway.dao.SubwayStationEntity;
import com.talkka.server.subway.dao.SubwayStationRepository;
import com.talkka.server.subway.dao.SubwayTimetableEntity;
import com.talkka.server.subway.dao.SubwayTimetableRepository;
import com.talkka.server.subway.dto.SubwayTimetableRespDto;
import com.talkka.server.subway.enums.DayType;
import com.talkka.server.subway.enums.Express;
import com.talkka.server.subway.enums.Line;
import com.talkka.server.subway.enums.Updown;
import com.talkka.server.subway.exception.StationNotFoundException;
import com.talkka.server.subway.exception.TimetableNotFoundException;

@ExtendWith(MockitoExtension.class)
public class SubwayTimetableServiceTest {

	@InjectMocks
	private SubwayTimetableService timetableService;

	@Mock
	private SubwayTimetableRepository timetableRepository;

	@Mock
	private SubwayStationRepository stationRepository;

	private final Long stationId = 1L;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm:ss");
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

	private SubwayTimetableEntity getTimetableEntity(Long stationId) {
		return SubwayTimetableEntity.builder()
			.id(1L)
			.subwayStation(getStationEntity(stationId))
			.stationName("서울역")
			.line(Line.LINE_ONE)
			.dayType(DayType.DAY)
			.updown(Updown.UP)
			.express(Express.NORMAL)
			.arrivalTime(LocalTime.of(8, 30))
			.startStationName("구로")
			.endStationName("동두천")
			.build();
	}

	@Nested
	@DisplayName("getTimetable 메서드 테스트")
	public class getTimetable {
		String time = "8:30:00";
		LocalTime parseTime = LocalTime.parse(time, formatter);

		@Test
		void 사용자가_원하는_지하철_역ID와_요일구분_상하구분_시간대를을_받아_해당하는_시간표_전체를_반환한다() {
			//given
			SubwayTimetableRespDto dto = SubwayTimetableRespDto.of(getTimetableEntity(stationId));

			given(stationRepository.existsById(stationId)).willReturn(true);
			given(timetableRepository.findBySubwayStationIdAndDayTypeAndUpdownAndArrivalTime(
				stationId,
				DayType.valueOfEnumString(dayType),
				Updown.valueOfEnumString(updown),
				parseTime)
			).willReturn(Optional.of(getTimetableEntity(stationId)));

			//when
			SubwayTimetableRespDto result = timetableService.getTimetable(
				stationId, dayType, updown, time);

			//then
			assertThat(result).isEqualTo(dto);
		}

		@Test
		void DateTime형식에_맞지_않게_넘어온_경우_Exceptio을_throw한다() {
			//given
			String wrongTime = "8:30:00.00";

			//when
			//then
			assertThatThrownBy(() -> timetableService.getTimetable(stationId, dayType, updown, wrongTime))
				.isInstanceOf(DateTimeParseException.class);
		}

		@Test
		void 존재하지_않는_지하철역_ID일_경우_Exception을_throw한다() {
			//given
			given(stationRepository.existsById(stationId)).willReturn(false);

			//when
			//then
			assertThatThrownBy(
				() -> timetableService.getTimetable(stationId, dayType, updown, time))
				.isInstanceOf(StationNotFoundException.class)
				.hasMessage("존재하지 않는 지하철 역입니다. StationId: " + stationId);
		}

		@Test
		void 시간표가_조회되지_않는_경우_Exception을_throw한다() {
			//given
			given(stationRepository.existsById(stationId)).willReturn(true);
			given(timetableRepository.findBySubwayStationIdAndDayTypeAndUpdownAndArrivalTime(
				stationId,
				DayType.valueOfEnumString(dayType),
				Updown.valueOfEnumString(updown),
				parseTime)
			).willReturn(Optional.empty());

			//when
			//then
			assertThatThrownBy(
				() -> timetableService.getTimetable(stationId, dayType, updown, time))
				.isInstanceOf(TimetableNotFoundException.class)
				.hasMessage("조회 가능한 시간표 정보가 없습니다.");
		}
	}

	@Nested
	@DisplayName("getTimetableList 메서드 테스트")
	public class getTimetableList {
		String startTime = "5:30:00";
		String endTime = "6:30:00";
		LocalTime start = LocalTime.parse(startTime, formatter);
		LocalTime end = LocalTime.parse(endTime, formatter);

		@Test
		void 사용자가_원하는_지하철_역ID와_요일구분_상하구분을_받아_해당하는_시간표_전체를_반환한다() {
			//given
			List<SubwayTimetableEntity> entityList = List.of(
				getTimetableEntity(1L),
				getTimetableEntity(1L)
			);

			List<SubwayTimetableRespDto> dtoList = entityList.stream()
				.map(SubwayTimetableRespDto::of)
				.toList();

			given(stationRepository.existsById(stationId)).willReturn(true);
			given(timetableRepository.findBySubwayStationIdAndDayTypeAndUpdownAndArrivalTimeBetween(
				stationId,
				DayType.valueOfEnumString(dayType),
				Updown.valueOfEnumString(updown),
				start,
				end)
			).willReturn(entityList);

			//when
			List<SubwayTimetableRespDto> result = timetableService.getTimetableList(
				stationId, dayType, updown, startTime, endTime);

			//then
			assertThat(result).isEqualTo(dtoList);
		}

		@Test
		void DateTime형식에_맞지_않게_넘어온_경우_Exceptio을_throw한다() {
			//given
			String wrongStartTime = "8:30:00.00";

			//when
			//then
			assertThatThrownBy(
				() -> timetableService.getTimetableList(stationId, dayType, updown, wrongStartTime, endTime))
				.isInstanceOf(DateTimeParseException.class);
		}

		@Test
		void 존재하지_않는_지하철역_ID일_경우_Exception을_throw한다() {
			//given
			given(stationRepository.existsById(stationId)).willReturn(false);

			//when
			//then
			assertThatThrownBy(
				() -> timetableService.getTimetableList(stationId, dayType, updown, startTime, endTime))
				.isInstanceOf(StationNotFoundException.class)
				.hasMessage("존재하지 않는 지하철 역입니다. StationId: " + stationId);
		}
	}
}
