package com.talkka.server.review.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
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
import com.talkka.server.common.enums.TimeSlot;
import com.talkka.server.common.exception.enums.InvalidTimeSlotEnumException;
import com.talkka.server.common.validator.ContentAccessValidator;
import com.talkka.server.review.dao.BusReviewEntity;
import com.talkka.server.review.dao.BusReviewRepository;
import com.talkka.server.review.dto.BusReviewDto;
import com.talkka.server.review.dto.BusReviewRespDto;
import com.talkka.server.review.exception.BusReviewNotFoundException;
import com.talkka.server.review.exception.BusRouteNotFoundException;
import com.talkka.server.review.exception.BusStationNotFoundException;
import com.talkka.server.review.exception.ContentAccessException;
import com.talkka.server.review.exception.UserNotFoundException;
import com.talkka.server.review.vo.Rating;
import com.talkka.server.review.vo.ReviewContent;
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

	@Mock
	private ContentAccessValidator contentAccessValidator;

	private final Long userId = 1L;
	private final Long busReviewId = 1L;
	private BusReviewDto busReviewDto;
	private BusReviewEntity busReviewEntity;
	private BusReviewEntity savedEntity;

	@BeforeEach
	public void setUp() {
		busReviewDto = BusReviewDto.builder()
			.id(busReviewId)
			.userId(userId)
			.routeId(236000050L)
			.busRouteStationId(1L)
			.content(new ReviewContent("리뷰 내용 123456678"))
			.timeSlot(TimeSlot.T_00_30)
			.rating(new Rating(5))
			.build();
		busReviewEntity = BusReviewEntity.builder()
			.id(busReviewId)
			.content(new ReviewContent("리뷰 내용 123456678"))
			.timeSlot(TimeSlot.T_00_00)
			.rating(new Rating(4))
			.writer(getUserFixture(userId))
			.route(getBusRouteFixture(236000050L))
			.station(getBusRouteStationFixture(1L))
			.build();
		savedEntity = BusReviewEntity.builder()
			.id(busReviewId)
			.content(busReviewDto.content())
			.timeSlot(busReviewDto.timeSlot())
			.rating(busReviewDto.rating())
			.writer(getUserFixture(userId))
			.route(getBusRouteFixture(236000050L))
			.station(getBusRouteStationFixture(1L))
			.build();
	}

	private UserEntity getUserFixture(Long userId) {
		return UserEntity.builder()
			.id(userId)
			.name("유저 이름")
			.build();
	}

	private BusRouteStationEntity getBusRouteStationFixture(Long busRouteStationId) {
		return BusRouteStationEntity.builder()
			.id(busRouteStationId)
			.stationName("정류장 이름")
			.build();
	}

	private BusRouteEntity getBusRouteFixture(Long routeId) {
		return BusRouteEntity.builder()
			.id(routeId)
			.routeName("노선 이름")
			.build();
	}

	@Nested
	@DisplayName("getBusReviewList 메서드 테스트")
	public class GetBusReviewListTest {
		private List<BusReviewEntity> reviewEntityList;

		@BeforeEach
		void setUp() {
			reviewEntityList = List.of(busReviewEntity);
		}

		@Test
		@DisplayName("routeId만 주어진 경우 BusReviewRepository의 findAllByRouteIdOrderByCreatedAtDesc 메서드를 호출한다.")
		void testGetBusReviewListWithRouteId() {
			// given
			given(busReviewRepository.findAllByRouteIdOrderByCreatedAtDesc(anyLong())).willReturn(reviewEntityList);
			// when
			busReviewService.getBusReviewList(236000050L);
			// then
			verify(busReviewRepository, times(1)).findAllByRouteIdOrderByCreatedAtDesc(anyLong());
		}

		@Test
		@DisplayName("routeId, busRouteStationId만 주어진 경우 BusReviewRepository의 findAllByRouteIdAndStationIdOrderByCreatedAtDesc 메서드를 호출한다.")
		void testGetBusReviewListWithRouteIdAndBusRouteStationId() {
			// given
			given(
				busReviewRepository.findAllByRouteIdAndStationIdOrderByCreatedAtDesc(anyLong(), anyLong())).willReturn(
				reviewEntityList);
			// when
			busReviewService.getBusReviewList(236000050L, 1L);
			// then
			verify(busReviewRepository, times(1)).findAllByRouteIdAndStationIdOrderByCreatedAtDesc(anyLong(),
				anyLong());
		}

		@Test
		@DisplayName("routeId, busRouteStationId, timeSlot만 주어진 경우 BusReviewRepository의 findAllByRouteIdAndStationIdAndTimeSlotOrderByCreatedAtDesc 메서드를 호출한다.")
		void testGetBusReviewListWithRouteIdAndBusRouteStationIdAndTimeSlot() {
			// given
			given(busReviewRepository.findAllByRouteIdAndStationIdAndTimeSlotOrderByCreatedAtDesc(anyLong(), anyLong(),
				any(TimeSlot.class))).willReturn(reviewEntityList);
			// when
			busReviewService.getBusReviewList(236000050L, 1L, "T_00_00");
			// then
			verify(busReviewRepository, times(1)).findAllByRouteIdAndStationIdAndTimeSlotOrderByCreatedAtDesc(anyLong(),
				anyLong(), any(TimeSlot.class));
		}

		@Test
		@DisplayName("TimeSlot이 잘못된 경우 InvalidTimeSlotEnumException을 발생시킨다.")
		void testInvalidTimeSlotEnumException() {
			// given
			// when
			// then
			assertThatThrownBy(() -> busReviewService.getBusReviewList(236000050L, 1L, "T_00_00_00"))
				.isInstanceOf(InvalidTimeSlotEnumException.class);
		}
	}

	@Nested
	@DisplayName("createBusReview 메서드 테스트")
	public class CreateBusReviewTest {
		@Test
		@DisplayName("리뷰 생성시, 생성된 리뷰의 dto를 반환한다.")
		void testCreateReviewReturnValue() {
			// given
			given(userRepository.findById(anyLong())).willReturn(Optional.of(getUserFixture(userId)));
			given(busRouteStationRepository.findById(anyLong())).willReturn(Optional.of(getBusRouteStationFixture(1L)));
			given(busRouteRepository.findById(anyLong())).willReturn(Optional.of(getBusRouteFixture(236000050L)));
			given(busReviewRepository.save(any())).willReturn(savedEntity);
			// when
			var result = busReviewService.createBusReview(busReviewDto);
			// then
			assertThat(result).isEqualTo(BusReviewRespDto.of(savedEntity));
		}

		@Test
		@DisplayName("리뷰 생성시, BusReviewRepository의 save 메서드를 호출한다.")
		void testCreateReview() {
			// given
			given(userRepository.findById(anyLong())).willReturn(Optional.of(getUserFixture(userId)));
			given(busRouteStationRepository.findById(anyLong())).willReturn(Optional.of(getBusRouteStationFixture(1L)));
			given(busRouteRepository.findById(anyLong())).willReturn(Optional.of(getBusRouteFixture(236000050L)));
			given(busReviewRepository.save(any())).willReturn(savedEntity);
			// when
			busReviewService.createBusReview(busReviewDto);
			// then
			verify(busReviewRepository, times(1)).save(any(BusReviewEntity.class));
		}

		@Test
		@DisplayName("리뷰 생성시, UserEntity가 없을 경우 UserNotFoundException을 발생시킨다.")
		void testUserNotFoundException() {
			// given
			given(userRepository.findById(anyLong())).willReturn(Optional.empty());
			// when
			// then
			assertThatThrownBy(() -> busReviewService.createBusReview(busReviewDto))
				.isInstanceOf(UserNotFoundException.class);
		}

		@Test
		@DisplayName("리뷰 생성시, BusRouteStationEntity가 없을 경우 BusStationNotFoundException을 발생시킨다.")
		void testBusStationNotFoundException() {
			// given
			given(userRepository.findById(anyLong())).willReturn(Optional.of(getUserFixture(userId)));
			given(busRouteStationRepository.findById(anyLong())).willReturn(Optional.empty());
			// when
			// then
			assertThatThrownBy(() -> busReviewService.createBusReview(busReviewDto))
				.isInstanceOf(BusStationNotFoundException.class);
		}

		@Test
		@DisplayName("리뷰 생성시, BusRouteEntity가 없을 경우 BusRouteNotFoundException을 발생시킨다.")
		void testBusRouteNotFoundException() {
			// given
			given(userRepository.findById(anyLong())).willReturn(Optional.of(getUserFixture(userId)));
			given(busRouteStationRepository.findById(anyLong())).willReturn(Optional.of(getBusRouteStationFixture(1L)));
			given(busRouteRepository.findById(anyLong())).willReturn(Optional.empty());
			// when
			// then
			assertThatThrownBy(() -> busReviewService.createBusReview(busReviewDto))
				.isInstanceOf(BusRouteNotFoundException.class);
		}
	}

	@Nested
	@DisplayName("updateBusReviewList 메서드 테스트")
	public class UpdateBusReviewTest {
		@Test
		@DisplayName("리뷰 업데이트 이후 업데이트된 리뷰의 dto를 반환한다.")
		void testUpdateReviewReturnValue() {
			// given
			given(userRepository.findById(anyLong())).willReturn(Optional.of(getUserFixture(userId)));
			given(busReviewRepository.findById(anyLong())).willReturn(Optional.of(busReviewEntity));
			given(busReviewRepository.save(any())).willReturn(savedEntity);
			// when
			var result = busReviewService.updateBusReview(busReviewDto);
			// then
			assertThat(result).isEqualTo(BusReviewRespDto.of(savedEntity));
		}

		@Test
		@DisplayName("리뷰 업데이트시, BusReviewRepository의 findById, save 메서드를 호출한다.")
		void testCallRepositoryMethods() {
			// given
			given(userRepository.findById(anyLong())).willReturn(Optional.of(getUserFixture(userId)));
			given(busReviewRepository.findById(anyLong())).willReturn(Optional.of(busReviewEntity));
			given(busReviewRepository.save(any())).willReturn(savedEntity);
			// when
			busReviewService.updateBusReview(busReviewDto);
			// then
			verify(busReviewRepository, times(1)).findById(anyLong());
			verify(busReviewRepository, times(1)).save(any(BusReviewEntity.class));
		}

		@Test
		@DisplayName("리뷰 업데이트시, BusReviewRepository의 findById 메서드가 리턴한 값이 없을 경우 BusReviewNotFoundException을 발생시킨다.")
		void testBusReviewNotFoundException() {
			// given
			given(userRepository.findById(anyLong())).willReturn(Optional.of(getUserFixture(userId)));
			given(busReviewRepository.findById(anyLong())).willReturn(Optional.empty());
			// when
			// then
			assertThatThrownBy(() -> busReviewService.updateBusReview(busReviewDto))
				.isInstanceOf(BusReviewNotFoundException.class);
		}

		@Test
		@DisplayName("리뷰 업데이트시, ContentAccessValidator의 validateOwnerContentAccess 메서드를 호출한다.")
		void testCallContentAccessValidator() {
			// given
			given(userRepository.findById(anyLong())).willReturn(Optional.of(getUserFixture(userId)));
			given(busReviewRepository.findById(anyLong())).willReturn(Optional.of(busReviewEntity));
			given(busReviewRepository.save(any())).willReturn(savedEntity);
			// when
			busReviewService.updateBusReview(busReviewDto);
			// then
			verify(contentAccessValidator, times(1)).validateOwnerContentAccess(anyLong(), any(), anyLong());
		}

		@Test
		@DisplayName("리뷰 업데이트시, 유저가 리뷰 작성자가 아닐 경우 ContentAccessException을 발생시킨다.")
		void testContentAccessException() {
			// given
			given(userRepository.findById(anyLong())).willReturn(Optional.of(getUserFixture(userId)));
			given(busReviewRepository.findById(anyLong())).willReturn(Optional.of(busReviewEntity));
			willThrow(new ContentAccessException()).given(contentAccessValidator)
				.validateOwnerContentAccess(anyLong(), any(), anyLong());
			// when
			// then
			assertThatThrownBy(() -> busReviewService.updateBusReview(busReviewDto))
				.isInstanceOf(ContentAccessException.class);
		}
	}

	@Nested
	@DisplayName("deleteBusReview 메서드")
	public class DeleteBusReviewTest {
		@Test
		@DisplayName("리뷰 삭제 이후 삭제된 리뷰의 id를 반환한다.")
		void testDeleteReviewReturnValue() {
			// given
			given(userRepository.findById(anyLong())).willReturn(Optional.of(getUserFixture(userId)));
			given(busReviewRepository.findById(anyLong())).willReturn(Optional.of(busReviewEntity));
			// when
			Long deletedReviewId = busReviewService.deleteBusReview(userId, busReviewId);
			// then
			assertThat(deletedReviewId).isEqualTo(busReviewId);
		}

		@Test
		@DisplayName("리뷰 삭제시, BusReviewRepository의 findById, deleteById 메서드를 호출한다.")
		void testCallRepositoryMethods() {
			// given
			given(userRepository.findById(anyLong())).willReturn(Optional.of(getUserFixture(userId)));
			given(busReviewRepository.findById(anyLong())).willReturn(Optional.of(busReviewEntity));
			// when
			busReviewService.deleteBusReview(userId, busReviewId);
			// then
			verify(busReviewRepository, times(1)).findById(anyLong());
			verify(busReviewRepository, times(1)).deleteById(anyLong());
		}

		@Test
		@DisplayName("리뷰 삭제시, BusReviewRepository의 findById 메서드가 리턴한 값이 없을 경우 BusReviewNotFoundException을 발생시킨다.")
		void testBusReviewNotFoundException() {
			// given
			given(userRepository.findById(anyLong())).willReturn(Optional.of(getUserFixture(userId)));
			given(busReviewRepository.findById(anyLong())).willReturn(Optional.empty());
			// when
			// then
			assertThatThrownBy(() -> busReviewService.deleteBusReview(userId, busReviewId))
				.isInstanceOf(BusReviewNotFoundException.class);
		}

		@Test
		@DisplayName("리뷰 삭제시, ContentAccessValidator의 validateOwnerContentAccess 메서드를 호출한다.")
		void testCallContentAccessValidator() {
			// given
			given(userRepository.findById(anyLong())).willReturn(Optional.of(getUserFixture(userId)));
			given(busReviewRepository.findById(anyLong())).willReturn(Optional.of(busReviewEntity));
			// when
			busReviewService.deleteBusReview(userId, busReviewId);
			// then
			verify(contentAccessValidator, times(1)).validateOwnerContentAccess(anyLong(), any(), anyLong());
		}

		@Test
		@DisplayName("리뷰 삭제시, 유저가 리뷰 작성자가 아닐 경우 ContentAccessException을 발생시킨다.")
		void testContentAccessException() {
			// given
			given(userRepository.findById(anyLong())).willReturn(Optional.of(getUserFixture(userId)));
			given(busReviewRepository.findById(anyLong())).willReturn(Optional.of(busReviewEntity));
			willThrow(new ContentAccessException()).given(contentAccessValidator)
				.validateOwnerContentAccess(anyLong(), any(), anyLong());
			// when
			// then
			assertThatThrownBy(() -> busReviewService.deleteBusReview(userId, busReviewId))
				.isInstanceOf(ContentAccessException.class);
		}
	}
}
