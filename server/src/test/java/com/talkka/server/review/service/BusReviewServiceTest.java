package com.talkka.server.review.service;

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

import com.talkka.server.bus.dao.BusRouteEntity;
import com.talkka.server.bus.dao.BusRouteRepository;
import com.talkka.server.bus.dao.BusRouteStationEntity;
import com.talkka.server.bus.dao.BusRouteStationRepository;
import com.talkka.server.common.exception.http.NotFoundException;
import com.talkka.server.review.dao.BusReviewEntity;
import com.talkka.server.review.dao.BusReviewRepository;
import com.talkka.server.review.dto.BusReviewReqDto;
import com.talkka.server.review.dto.BusReviewRespDto;
import com.talkka.server.user.dao.UserEntity;
import com.talkka.server.user.dao.UserRepository;

@ExtendWith(MockitoExtension.class)
public class BusReviewServiceTest {

	@InjectMocks
	private BusReviewService busReviewService;

	@Mock
	private BusReviewRepository busReviewRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private BusRouteStationRepository busRouteStationRepository;

	@Mock
	private BusRouteRepository busRouteRepository;

	private BusReviewRespDto busReviewRespDtoFixture(Long userId) {
		return BusReviewRespDto.builder()
			.userId(userId)
			.routeId(236000050L)
			.busRouteStationId(1L)
			.content(Optional.of("리뷰 내용"))
			.timeSlot(24)
			.rating(4)
			.build();
	}

	final UserEntity user = UserEntity.builder()
		.userId(1L)
		.build();
	final BusRouteStationEntity station = BusRouteStationEntity.builder()
		.busRouteStationId(1L)
		.build();
	final BusRouteEntity route = BusRouteEntity.builder()
		.routeId(236000050L)
		.build();

	@Nested
	@DisplayName("createBusReview 메서드 테스트")
	public class createBusReviewTest {

		@Test
		void 제한된_요청에_따라_버스리뷰를_생성한다() {
			//given
			final BusReviewReqDto busReviewReqDto = BusReviewReqDto.builder()
				.busRouteStationId(1L)
				.routeId(236000050L)
				.content(Optional.of("리뷰 내용"))
				.timeSlot(24)
				.rating(4)
				.build();

			final BusReviewEntity busReviewEntity = BusReviewEntity.builder()
				.content("리뷰 내용")
				.timeSlot(24)
				.rating(4)
				.writer(user)
				.station(station)
				.route(route)
				.build();

			given(userRepository.findById(1L)).willReturn(Optional.of(user));
			given(busRouteStationRepository.findById(1L)).willReturn(Optional.of(station));
			given(busRouteRepository.findById(236000050L)).willReturn(Optional.of(route));
			given(busReviewRepository.save(any(BusReviewEntity.class))).willReturn(busReviewEntity);

			final BusReviewRespDto resultDto = busReviewRespDtoFixture(1L);

			//when
			final BusReviewRespDto result = busReviewService.createBusReview(1L, busReviewReqDto);

			//then
			assertThat(result).isEqualTo(resultDto);
		}

		@Test
		void 유저를_못찾을_경우_Exception을_throw_한다() {
			//given
			final Class<?> exceptionClass = NotFoundException.class; // 추후 변경될 가능성이 있어, 변수로 따로 지정함
			BusReviewReqDto busReviewReqDto = BusReviewReqDto.builder()
				.build();

			//when
			//then
			assertThatThrownBy(() -> busReviewService.createBusReview(null, busReviewReqDto))
				.isInstanceOf(exceptionClass)
				.hasMessage("존재하지 않는 유저입니다.");
		}

		@Test
		void 경유_정류장을_못찾을_경우_Exceptio을_throw_한다() {
			//given
			final Class<?> exceptionClass = NotFoundException.class; // 추후 변경될 가능성이 있어, 변수로 따로 지정함
			final BusReviewReqDto busReviewReqDto = BusReviewReqDto.builder()
				.busRouteStationId(null)
				.routeId(236000050L)
				.content(Optional.of("리뷰 내용"))
				.timeSlot(24)
				.rating(4)
				.build();

			given(userRepository.findById(1L)).willReturn(Optional.of(user));

			//when
			//then
			assertThatThrownBy(() -> busReviewService.createBusReview(1L, busReviewReqDto))
				.isInstanceOf(exceptionClass)
				.hasMessage("존재하지 않는 경유 정류장입니다.");
		}

		@Test
		void 노선을_못찾을_경우_Exceptio을_throw_한다() {
			//given
			final Class<?> exceptionClass = NotFoundException.class; // 추후 변경될 가능성이 있어, 변수로 따로 지정함
			final BusReviewReqDto busReviewReqDto = BusReviewReqDto.builder()
				.busRouteStationId(1L)
				.routeId(null)
				.content(Optional.of("리뷰 내용"))
				.timeSlot(24)
				.rating(4)
				.build();

			given(userRepository.findById(1L)).willReturn(Optional.of(user));
			given(busRouteStationRepository.findById(1L)).willReturn(Optional.of(station));

			//when
			//then
			assertThatThrownBy(() -> busReviewService.createBusReview(1L, busReviewReqDto))
				.isInstanceOf(exceptionClass)
				.hasMessage("존재하지 않는 노선입니다.");
		}
	}

	@Nested
	@DisplayName("updateBusReviewList 메서드 테스트")
	public class updateBusReviewTest {

		BusReviewReqDto busReviewReqDto = BusReviewReqDto.builder()
			.routeId(236000050L)
			.busRouteStationId(1L)
			.content(Optional.of("변경된 리뷰 내용"))
			.timeSlot(23)
			.rating(5)
			.build();

		@Test
		void 리뷰를_찾지_못하는_경우_Exception을_throw_한다() {
			//given
			final Class<?> exceptionClass = NotFoundException.class; // 추후 변경될 가능성이 있어, 변수로 따로 지정함

			//when
			//then
			assertThatThrownBy(
				() -> busReviewService.updateBusReview(1L, busReviewReqDto))
				.isInstanceOf(exceptionClass)
				.hasMessage("존재하지 않는 리뷰입니다.");
		}

		@Test
		void 리뷰를_업데이트_한_경우_업데이트_된_BusReviewEntity의_BusReviewRespDto를_반환한다() {
			//given
			BusReviewEntity originEntity = BusReviewEntity.builder()
				.busReviewId(1L)
				.content("리뷰 내용")
				.timeSlot(24)
				.rating(4)
				.writer(user)
				.station(station)
				.route(route)
				.build();

			BusReviewEntity updatedEntity = busReviewReqDto.toEntity(user, station, route);

			given(busReviewRepository.findById(1L)).willReturn(Optional.of(originEntity));
			given(busReviewRepository.save(originEntity)).willReturn(updatedEntity);

			BusReviewRespDto resultDto = BusReviewRespDto.of(updatedEntity);

			//when
			BusReviewRespDto updatedReview = busReviewService.updateBusReview(1L, busReviewReqDto);

			//then
			assertThat(updatedReview).isEqualTo(resultDto);
		}
	}
}
