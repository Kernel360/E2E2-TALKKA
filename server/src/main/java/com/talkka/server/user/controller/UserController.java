package com.talkka.server.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talkka.server.common.dto.ApiRespDto;
import com.talkka.server.user.dto.UserCreateDto;
import com.talkka.server.user.dto.UserCreateReqDto;
import com.talkka.server.user.dto.UserRespDto;
import com.talkka.server.user.dto.UserUpdateReqDto;
import com.talkka.server.user.enums.Grade;
import com.talkka.server.user.service.UserService;

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
		final UserRespDto userRespDto = UserRespDto.of(userService.getUser(userId));
		return ResponseEntity.ok(
			ApiRespDto.<UserRespDto>builder()
				.statusCode(200)
				.message("OK")
				.data(userRespDto)
				.build()
		);
	}

	@PostMapping("")
	public ResponseEntity<ApiRespDto<UserRespDto>> createUser(@RequestBody UserCreateReqDto userCreateReqDto) {
		// Session 연결 이후에 재수정해야함.
		final UserCreateDto userCreateDto = new UserCreateDto(
			"name",
			"test@test.com",
			"naver",
			userCreateReqDto.getNickname(),
			"token",
			Grade.USER
		);
		final UserRespDto userRespDto = UserRespDto.of(userService.createUser(userCreateDto));
		return ResponseEntity.ok(
			ApiRespDto.<UserRespDto>builder()
				.statusCode(200)
				.message("OK")
				.data(userRespDto)
				.build()
		);
	}

	@PutMapping("/{user_id}")
	public ResponseEntity<ApiRespDto<UserRespDto>> updateUser(@PathVariable("user_id") Long userId,
		@RequestBody UserUpdateReqDto userUpdateReqDto) {
		final UserRespDto userRespDto = UserRespDto.of(
			userService.updateUser(userId, userUpdateReqDto));
		return ResponseEntity.ok(
			ApiRespDto.<UserRespDto>builder()
				.statusCode(200)
				.message("OK")
				.data(userRespDto)
				.build()
		);
	}

	@DeleteMapping("/{user_id}")
	public ResponseEntity<ApiRespDto<Long>> deleteUser(@PathVariable("user_id") Long userId) {
		final Long deletedUserId = userService.deleteUser(userId);
		return ResponseEntity.ok(
			ApiRespDto.<Long>builder()
				.statusCode(200)
				.message("OK")
				.data(null)
				.build()
		);
	}
}
