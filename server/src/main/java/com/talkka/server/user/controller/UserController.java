package com.talkka.server.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talkka.server.common.dto.ApiRespDto;
import com.talkka.server.common.enums.StatusCode;
import com.talkka.server.oauth.domain.OAuth2UserInfo;
import com.talkka.server.user.dto.UserCreateDto;
import com.talkka.server.user.dto.UserCreateReqDto;
import com.talkka.server.user.dto.UserDto;
import com.talkka.server.user.dto.UserRespDto;
import com.talkka.server.user.dto.UserUpdateReqDto;
import com.talkka.server.user.enums.Grade;
import com.talkka.server.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@GetMapping("/{user_id}")
	public ResponseEntity<ApiRespDto<UserRespDto>> getUser(
		@PathVariable("user_id") Long userId
	) {
		UserRespDto userRespDto = UserRespDto.of(userService.getUser(userId));
		return ResponseEntity.ok(
			ApiRespDto.<UserRespDto>builder()
				.statusCode(StatusCode.OK.getCode())
				.message(StatusCode.OK.getMessage())
				.data(userRespDto)
				.build()
		);
	}

	@PostMapping("")
	public ResponseEntity<ApiRespDto<UserRespDto>> createUser(@RequestBody @Valid UserCreateReqDto userCreateReqDto) {
		// Session 연결 이후에 재수정해야함.
		UserCreateDto userCreateDto = new UserCreateDto(
			"name",
			"test@test.com",
			"naver",
			userCreateReqDto.getNickname(),
			"token",
			Grade.USER
		);
		UserRespDto userRespDto = UserRespDto.of(userService.createUser(userCreateDto));
		return ResponseEntity.ok(
			ApiRespDto.<UserRespDto>builder()
				.statusCode(StatusCode.OK.getCode())
				.message(StatusCode.OK.getMessage())
				.data(userRespDto)
				.build()
		);
	}

	@PutMapping("/{user_id}")
	public ResponseEntity<ApiRespDto<UserRespDto>> updateUser(@PathVariable("user_id") Long userId,
		@RequestBody @Valid UserUpdateReqDto userUpdateReqDto) {
		UserRespDto userRespDto = UserRespDto.of(
			userService.updateUser(userId, userUpdateReqDto));
		return ResponseEntity.ok(
			ApiRespDto.<UserRespDto>builder()
				.statusCode(StatusCode.OK.getCode())
				.message(StatusCode.OK.getMessage())
				.data(userRespDto)
				.build()
		);
	}

	@DeleteMapping("/{user_id}")
	public ResponseEntity<ApiRespDto<Long>> deleteUser(@PathVariable("user_id") Long userId) {
		Long deletedUserId = userService.deleteUser(userId);
		return ResponseEntity.ok(
			ApiRespDto.<Long>builder()
				.statusCode(StatusCode.OK.getCode())
				.message(StatusCode.OK.getMessage())
				.data(null)
				.build()
		);
	}

	@GetMapping("/me")
	public ResponseEntity<ApiRespDto<UserRespDto>> getMe(@AuthenticationPrincipal OAuth2UserInfo userInfo) {
		UserRespDto userRespDto = UserRespDto.of(userService.getUser(userInfo.getUserId()));
		return ResponseEntity.ok(
			ApiRespDto.<UserRespDto>builder()
				.statusCode(StatusCode.OK.getCode())
				.message(StatusCode.OK.getMessage())
				.data(userRespDto)
				.build()
		);
	}

	@PutMapping("/me")
	public ResponseEntity<ApiRespDto<UserRespDto>> updateMe(@AuthenticationPrincipal OAuth2UserInfo userInfo,
		@RequestBody @Valid UserUpdateReqDto userUpdateReqDto) {
		UserDto userDto = userService.updateUser(userInfo.getUserId(), userUpdateReqDto);
		UserRespDto userRespDto = UserRespDto.of(userDto);
		return ResponseEntity.ok(
			ApiRespDto.<UserRespDto>builder()
				.statusCode(StatusCode.OK.getCode())
				.message(StatusCode.OK.getMessage())
				.data(userRespDto)
				.build()
		);
	}
}
