package com.talkka.server.user.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.talkka.server.common.exception.http.BadRequestException;
import com.talkka.server.common.exception.http.NotFoundException;
import com.talkka.server.user.dao.UserEntity;
import com.talkka.server.user.dao.UserRepository;
import com.talkka.server.user.dto.UserCreateDto;
import com.talkka.server.user.dto.UserDto;
import com.talkka.server.user.dto.UserUpdateReqDto;
import com.talkka.server.user.enums.Grade;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	private UserDto userDtoFixture(Long userId) {
		return new UserDto(
			userId,
			"name",
			"email",
			"nickname",
			"oauthProvider",
			"accessToken",
			Grade.USER,
			LocalDateTime.now(),
			LocalDateTime.now()
		);
	}

	@Nested
	@DisplayName("GetUser 메소드 테스트")
	public class GetUserTest {
		@Test
		public void 유저의_아이디를_받아_유저를_반환한다() {
			// given
			UserDto userDto = userDtoFixture(1L);
			given(userRepository.findById(1L)).willReturn(Optional.of(userDto.toEntity()));
			// when
			var result = userService.getUser(1L);
			// then
			assertThat(result).isEqualTo(result);
		}

		@Test
		void 존재하지_않는_유저를_조회할_경우_Exception을_throw_한다() {
			// given
			Class<?> exceptionClass = NotFoundException.class; // 추후 변경될 가능성이 있어, 변수로 따로 지정함
			given(userRepository.findById(1L)).willReturn(Optional.empty());
			// when
			// then
			assertThatThrownBy(() -> userService.getUser(1L)).isInstanceOf(exceptionClass);
		}
	}

	@Nested
	@DisplayName("CreateUser 메소드 테스트")
	public class CreateUserTest {
		@Test
		void 제안된_요청에_따라_유저를_생성한다() {
			// given
			UserCreateDto userCreateDto = new UserCreateDto(
				"name",
				"email",
				"nickname",
				"oauthProvider",
				"accessToken",
				Grade.USER
			);
			UserDto resultDto = userDtoFixture(1L);
			given(userRepository.save(any(UserEntity.class))).willReturn(resultDto.toEntity());
			// when
			var result = userService.createUser(userCreateDto);
			// then
			assertThat(result.userId()).isEqualTo(1L);
			assertThat(result.name()).isEqualTo(userCreateDto.name());
			assertThat(result.email()).isEqualTo(userCreateDto.email());
			assertThat(result.nickname()).isEqualTo(userCreateDto.nickname());
			assertThat(result.oauthProvider()).isEqualTo(userCreateDto.oauthProvider());
			assertThat(result.accessToken()).isEqualTo(userCreateDto.accessToken());
			assertThat(result.grade()).isEqualTo(userCreateDto.grade());
		}

		@Test
		void 중복된_닉네임이_이미_존재하는_경우_Exception을_throw_한다() {
			// given
			UserDto userDto = userDtoFixture(1L);
			UserCreateDto userCreateDto = new UserCreateDto(
				"name",
				"email",
				"oauthProvider",
				userDto.nickname(),
				"accessToken",
				Grade.USER
			);
			Class<?> exceptionClass = BadRequestException.class; // 추후 변경될 가능성이 있어, 변수로 따로 지정함
			given(userService.isDuplicatedNickname(any())).willReturn(true);
			// when
			// then
			assertThatThrownBy(() -> userService.createUser(userCreateDto)).isInstanceOf(exceptionClass);
		}
	}

	@Nested
	@DisplayName("isDuplicatedNickname 메소드 테스트")
	public class isDuplicatedNicknameTest {
		@Test
		void 닉네임이_중복되지_않는경우_false를_반환한다() {
			// given
			String nickname = "nickname";
			given(userRepository.existsByNickname(nickname)).willReturn(false);
			// when
			var result = userService.isDuplicatedNickname(nickname);
			// then
			assertThat(result).isFalse();
		}

		@Test
		void 닉네임이_중복되는경우_true를_반환한다() {
			// given
			String nickname = "nickname";
			given(userRepository.existsByNickname(nickname)).willReturn(true);
			// when
			var result = userService.isDuplicatedNickname(nickname);
			// then
			assertThat(result).isTrue();
		}
	}

	@Nested
	@DisplayName("UpdateUser 메소드 테스트")
	public class UpdateUserTest {
		@Test
		void 유저가_수정할_정보를_요청하면_수정한_정보를_반환한다() {
			// given
			UserUpdateReqDto reqDto = UserUpdateReqDto.builder()
				.nickname("nickname2")
				.build();
			UserDto findDto = userDtoFixture(1L);
			UserEntity findEntity = findDto.toEntity();
			LocalDateTime now = LocalDateTime.now();

			given(userRepository.findById(1L)).willReturn(Optional.of(findEntity));
			// when
			var result = userService.updateUser(1L, reqDto);
			// then
			assertThat(result.userId()).isEqualTo(1L);
			assertThat(result.nickname()).isEqualTo("nickname2");
			assertThat(result.oauthProvider()).isEqualTo(findDto.oauthProvider());
			assertThat(result.accessToken()).isEqualTo(findDto.accessToken());
			assertThat(result.grade()).isEqualTo(Grade.USER);
			assertThat(result.createdAt()).isEqualTo(findDto.createdAt());
		}

		@Test
		void 존재하지_않는_유저를_수정할_경우_Exception을_throw_한다() {
			// given
			UserUpdateReqDto reqDto = UserUpdateReqDto.builder()
				.nickname("nickname2")
				.build();
			Class<?> exceptionClass = BadRequestException.class; // 추후 변경될 가능성이 있어, 변수로 따로 지정함
			given(userRepository.findById(1L)).willReturn(Optional.empty());
			// when
			// then
			assertThatThrownBy(() -> userService.updateUser(1L, reqDto)).isInstanceOf(exceptionClass);
		}

		@Test
		void 중복된_닉네임으로_수정할_경우_Exception을_throw_한다() {
			// given
			UserUpdateReqDto reqDto = new UserUpdateReqDto("nickname2");
			Class<?> exceptionClass = BadRequestException.class; // 추후 변경될 가능성이 있어, 변수로 따로 지정함
			UserDto findDto = userDtoFixture(1L);
			UserEntity findEntity = findDto.toEntity();
			given(userRepository.findById(1L)).willReturn(Optional.of(findEntity));
			given(userService.isDuplicatedNickname(reqDto.nickname())).willReturn(true);
			// when
			// then
			assertThatThrownBy(() -> userService.updateUser(1L, reqDto)).isInstanceOf(exceptionClass)
				.hasMessage("중복된 닉네임 입니다.");
		}
	}

	@Nested
	@DisplayName("DeleteUser 메소드 테스트")
	public class DeleteUserTest {
		@Test
		void 유저를_삭제한다() {
			// given
			given(userRepository.existsById(1L)).willReturn(true);
			// when
			Long result = userService.deleteUser(1L);
			// then
			then(userRepository).should().deleteById(1L);
			assertThat(result).isEqualTo(1L);
		}

		@Test
		void 존재하지_않는_유저를_삭제할_경우_Exception을_throw_한다() {
			// given
			Class<?> exceptionClass = BadRequestException.class; // 추후 변경될 가능성이 있어, 변수로 따로 지정함
			given(userRepository.existsById(1L)).willReturn(false);
			// when
			// then
			assertThatThrownBy(() -> userService.deleteUser(1L)).isInstanceOf(exceptionClass);
		}
	}
}