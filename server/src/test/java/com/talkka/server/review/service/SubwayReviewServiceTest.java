package com.talkka.server.review.service;

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
import com.talkka.server.common.validator.ContentAccessValidator;
import com.talkka.server.review.dao.SubwayReviewEntity;
import com.talkka.server.review.dao.SubwayReviewRepository;
import com.talkka.server.review.dto.SubwayReviewDto;
import com.talkka.server.review.dto.SubwayReviewRespDto;
import com.talkka.server.review.exception.ContentAccessException;
import com.talkka.server.review.exception.SubwayReviewNotFoundException;
import com.talkka.server.review.exception.UserNotFoundException;
import com.talkka.server.review.vo.Rating;
import com.talkka.server.review.vo.ReviewContent;
import com.talkka.server.subway.dao.SubwayStationEntity;
import com.talkka.server.subway.dao.SubwayStationRepository;
import com.talkka.server.subway.enums.Line;
import com.talkka.server.subway.enums.Updown;
import com.talkka.server.subway.exception.StationNotFoundException;
import com.talkka.server.user.dao.UserEntity;
import com.talkka.server.user.dao.UserRepository;

@ExtendWith(MockitoExtension.class)
public class SubwayReviewServiceTest {

	@InjectMocks
	private SubwayReviewService reviewService;

	@Mock
	private SubwayReviewRepository reviewRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private SubwayStationRepository stationRepository;

	@Mock
	private ContentAccessValidator contentAccessValidator;

	private final Long userId = 1L;
	private final Long stationId = 1L;
	private final Long subwayReviewId = 1L;

	private final SubwayReviewDto reviewDto = SubwayReviewDto.builder()
		.id(subwayReviewId)
		.userId(1L)
		.stationId(1L)
		.line(Line.LINE_ONE)
		.updown(Updown.UP)
		.content(new ReviewContent("리뷰 내용입니다."))
		.timeSlot(TimeSlot.T_08_00)
		.rating(new Rating(4))
		.build();

	private final SubwayReviewEntity savedEntity = SubwayReviewEntity.builder()
		.id(subwayReviewId)
		.writer(getUserFixture(userId))
		.station(getStationFixture(stationId))
		.line(reviewDto.line())
		.updown(reviewDto.updown())
		.content(reviewDto.content())
		.timeSlot(reviewDto.timeSlot())
		.rating(reviewDto.rating())
		.build();

	private final SubwayReviewEntity updateReviewEntity = SubwayReviewEntity.builder()
		.id(subwayReviewId)
		.writer(getUserFixture(userId))
		.station(getStationFixture(stationId))
		.line(Line.LINE_ONE)
		.updown(Updown.UP)
		.content(new ReviewContent("수정된 리뷰 내용입니다."))
		.timeSlot(TimeSlot.T_08_00)
		.rating(new Rating(5))
		.build();

	private UserEntity getUserFixture(Long userId) {
		return UserEntity.builder()
			.id(userId)
			.name("유저 이름")
			.build();
	}

	private SubwayStationEntity getStationFixture(Long stationId) {
		return SubwayStationEntity.builder()
			.id(stationId)
			.stationCode("0150")
			.stationName("서울역")
			.line(Line.LINE_ONE)
			.build();
	}

	private SubwayReviewEntity getReviewFixture(Long userId, Long stationId) {
		return reviewDto.toEntity(getUserFixture(userId), getStationFixture(stationId));
	}

	@Nested
	@DisplayName("getSubwayReviewList 메서드 테스트")
	public class getSubwayReviewListTest {

		List<SubwayReviewEntity> entityList = List.of();

		@Test
		@DisplayName("stationId만 주어진 경우 SubwayReviewRepository의 findAllByStationIdOrderByCreatedAtDesc 메서드를 호출한다.")
		void testGetSubwayReviewListWithStationId() {
			//given
			given(reviewRepository.findAllByStationIdOrderByCreatedAtDesc(anyLong())).willReturn(entityList);

			//when
			reviewService.getSubwayReviewList(1L);

			//then
			verify(reviewRepository, times(1)).findAllByStationIdOrderByCreatedAtDesc(anyLong());
		}

		@Test
		@DisplayName("stationId와 updown이 주어진 경우 SubwayReviewRepository의 findAllByStationIdAndUpdownOrderByCreatedAtDesc 메서드를 호출한다.")
		void testGetSubwayReviewListWithStationIdAndUpdown() {
			//given
			given(reviewRepository.findAllByStationIdAndUpdownOrderByCreatedAtDesc(
				anyLong(), any(Updown.class))).willReturn(entityList);

			//when
			reviewService.getSubwayReviewList(1L, Updown.UP.toString());

			//then
			verify(reviewRepository, times(1)).findAllByStationIdAndUpdownOrderByCreatedAtDesc(
				anyLong(), any(Updown.class));
		}

		@Test
		@DisplayName("stationId와 updown, timeSlot이 주어진 경우 SubwayReviewRepository의 findAllByStationIdAndUpdownAndTimeSlotOrderByCreatedAtDesc 메서드를 호출한다.")
		void testGetSubwayReviewListWithStationIdAndUpdownAndTimeSlot() {
			//given
			given(reviewRepository.findAllByStationIdAndUpdownAndTimeSlotOrderByCreatedAtDesc(
				anyLong(), any(Updown.class), any(TimeSlot.class))).willReturn(entityList);

			//when
			reviewService.getSubwayReviewList(1L, Updown.UP.toString(), TimeSlot.T_08_00.toString());

			//then
			verify(reviewRepository, times(1)).findAllByStationIdAndUpdownAndTimeSlotOrderByCreatedAtDesc(
				anyLong(), any(Updown.class), any(TimeSlot.class));
		}
	}

	@Nested
	@DisplayName("createSubwayReview 메서드 테스트")
	public class createSubwayReviewTest {

		@Test
		@DisplayName("리뷰 생성시, 생성된 리뷰의 dto를 반환한다.")
		void testCreateReviewReturnValue() {
			//given
			given(userRepository.findById(userId)).willReturn(Optional.of(getUserFixture(userId)));
			given(stationRepository.findById(stationId)).willReturn(Optional.of(getStationFixture(stationId)));
			given(reviewRepository.save(reviewDto.toEntity(getUserFixture(userId), getStationFixture(stationId))))
				.willReturn(savedEntity);

			//when
			SubwayReviewRespDto respDto = reviewService.createSubwayReview(reviewDto);

			//then
			assertThat(respDto).isEqualTo(SubwayReviewRespDto.of(savedEntity));
		}

		@Test
		@DisplayName("리뷰 생성시, SubwayReviewRepository의 save 메서드를 호출한다.")
		void testCreateReview() {
			//given
			given(userRepository.findById(userId)).willReturn(Optional.of(getUserFixture(userId)));
			given(stationRepository.findById(stationId)).willReturn(Optional.of(getStationFixture(stationId)));
			given(reviewRepository.save(reviewDto.toEntity(getUserFixture(userId), getStationFixture(stationId))))
				.willReturn(savedEntity);

			//when
			reviewService.createSubwayReview(reviewDto);

			//then
			verify(reviewRepository, times(1)).save(any(SubwayReviewEntity.class));
		}

		@Test
		@DisplayName("리뷰 생성시, UserEntity가 없을 경우 UserNotFoundException을 발생시킨다.")
		void testUserNotFoundException() {
			//given
			given(userRepository.findById(userId)).willReturn(Optional.empty());

			//when
			//then
			assertThatThrownBy(() -> reviewService.createSubwayReview(reviewDto))
				.isInstanceOf(UserNotFoundException.class)
				.hasMessage("존재하지 않는 사용자입니다.");
		}

		@Test
		@DisplayName("리뷰 생성시, SubwayStationEntity가 없을 경우 StationNotFoundException 발생시킨다.")
		void testSubwayStationNotFoundException() {
			//given
			given(userRepository.findById(userId)).willReturn(Optional.of(getUserFixture(userId)));
			given(stationRepository.findById(stationId)).willReturn(Optional.empty());
			//when
			//then
			assertThatThrownBy(() -> reviewService.createSubwayReview(reviewDto))
				.isInstanceOf(StationNotFoundException.class)
				.hasMessage("존재하지 않는 지하철 역입니다. StationId: " + stationId);
		}
	}

	@Nested
	@DisplayName("updateSubwayReviewList 메서드 테스트")
	public class updateSubwayReviewTest {

		@Test
		@DisplayName("리뷰 업데이트 이후 업데이트된 리뷰의 dto를 반환한다.")
		void testUpdateReviewReturnValue() {
			// given
			given(userRepository.findById(anyLong())).willReturn(Optional.of(getUserFixture(userId)));
			given(reviewRepository.findById(anyLong())).willReturn(Optional.of(savedEntity));
			given(reviewRepository.save(any())).willReturn(updateReviewEntity);
			// when
			var result = reviewService.updateSubwayReview(reviewDto);
			// then
			assertThat(result).isEqualTo(SubwayReviewRespDto.of(updateReviewEntity));
		}

		@Test
		@DisplayName("리뷰 업데이트시, SubwayReviewRepository의 findById, save 메서드를 호출한다.")
		void testCallRepositoryMethods() {
			// given
			given(userRepository.findById(anyLong())).willReturn(Optional.of(getUserFixture(userId)));
			given(reviewRepository.findById(anyLong())).willReturn(Optional.of(savedEntity));
			given(reviewRepository.save(reviewDto.toEntity(getUserFixture(userId), getStationFixture(stationId))))
				.willReturn(updateReviewEntity);
			// when
			reviewService.updateSubwayReview(reviewDto);
			// then
			verify(reviewRepository, times(1)).findById(anyLong());
			verify(reviewRepository, times(1)).save(any(SubwayReviewEntity.class));
		}

		@Test
		@DisplayName("리뷰 업데이트시, SubwayReviewRepository의 findById 메서드가 리턴한 값이 없을 경우 SubwayReviewNotFoundException을 발생시킨다.")
		void testBusReviewNotFoundException() {
			// given
			given(userRepository.findById(anyLong())).willReturn(Optional.of(getUserFixture(userId)));
			given(reviewRepository.findById(anyLong())).willReturn(Optional.empty());
			// when
			// then
			assertThatThrownBy(() -> reviewService.updateSubwayReview(reviewDto))
				.isInstanceOf(SubwayReviewNotFoundException.class);
		}

		@Test
		@DisplayName("리뷰 업데이트시, ContentAccessValidator의 validateOwnerContentAccess 메서드를 호출한다.")
		void testCallContentAccessValidator() {
			// given
			given(userRepository.findById(anyLong())).willReturn(Optional.of(getUserFixture(userId)));
			given(reviewRepository.findById(anyLong())).willReturn(Optional.of(savedEntity));
			given(reviewRepository.save(reviewDto.toEntity(getUserFixture(userId), getStationFixture(stationId))))
				.willReturn(getReviewFixture(userId, stationId));
			// when
			reviewService.updateSubwayReview(reviewDto);
			// then
			verify(contentAccessValidator, times(1)).validateOwnerContentAccess(anyLong(), any(), anyLong());
		}

		@Test
		@DisplayName("리뷰 업데이트시, 유저가 리뷰 작성자가 아닐 경우 ContentAccessException을 발생시킨다.")
		void testContentAccessException() {
			// given
			given(userRepository.findById(anyLong())).willReturn(Optional.of(getUserFixture(userId)));
			given(reviewRepository.findById(anyLong())).willReturn(Optional.of(savedEntity));
			willThrow(new ContentAccessException()).given(contentAccessValidator)
				.validateOwnerContentAccess(anyLong(), any(), anyLong());
			// when
			// then
			assertThatThrownBy(() -> reviewService.updateSubwayReview(reviewDto))
				.isInstanceOf(ContentAccessException.class);
		}
	}

	@Nested
	@DisplayName("deleteSubwayReview 메서드 테스트")
	public class deleteSubwayReviewTest {
		@Test
		@DisplayName("리뷰 삭제시, SubwayReviewRepository의 findById, deleteById 메서드를 호출한다.")
		void testCallRepositoryMethods() {
			// given
			given(userRepository.findById(anyLong())).willReturn(Optional.of(getUserFixture(userId)));
			given(reviewRepository.findById(anyLong())).willReturn(Optional.of(savedEntity));
			// when
			reviewService.deleteSubwayReview(userId, subwayReviewId);
			// then
			verify(reviewRepository, times(1)).findById(anyLong());
			verify(reviewRepository, times(1)).deleteById(anyLong());
		}

		@Test
		@DisplayName("리뷰 삭제시, SubwayReviewRepository의 findById 메서드가 리턴한 값이 없을 경우 SubwayReviewNotFoundException을 발생시킨다.")
		void testBusReviewNotFoundException() {
			// given
			given(userRepository.findById(anyLong())).willReturn(Optional.of(getUserFixture(userId)));
			given(reviewRepository.findById(anyLong())).willReturn(Optional.empty());
			// when
			// then
			assertThatThrownBy(() -> reviewService.deleteSubwayReview(userId, subwayReviewId))
				.isInstanceOf(SubwayReviewNotFoundException.class);
		}

		@Test
		@DisplayName("리뷰 삭제시, ContentAccessValidator의 validateOwnerContentAccess 메서드를 호출한다.")
		void testCallContentAccessValidator() {
			// given
			given(userRepository.findById(anyLong())).willReturn(Optional.of(getUserFixture(userId)));
			given(reviewRepository.findById(anyLong())).willReturn(Optional.of(savedEntity));
			// when
			reviewService.deleteSubwayReview(userId, subwayReviewId);
			// then
			verify(contentAccessValidator, times(1)).validateOwnerContentAccess(anyLong(), any(), anyLong());
		}

		@Test
		@DisplayName("리뷰 삭제시, 유저가 리뷰 작성자가 아닐 경우 ContentAccessException을 발생시킨다.")
		void testContentAccessException() {
			// given
			given(userRepository.findById(anyLong())).willReturn(Optional.of(getUserFixture(userId)));
			given(reviewRepository.findById(anyLong())).willReturn(Optional.of(savedEntity));
			willThrow(new ContentAccessException()).given(contentAccessValidator)
				.validateOwnerContentAccess(anyLong(), any(), anyLong());
			// when
			// then
			assertThatThrownBy(() -> reviewService.deleteSubwayReview(userId, subwayReviewId))
				.isInstanceOf(ContentAccessException.class);
		}
	}
}
