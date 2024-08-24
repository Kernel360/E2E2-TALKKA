package com.talkka.server.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talkka.server.common.dto.ErrorRespDto;
import com.talkka.server.common.exception.InvalidTypeException;
import com.talkka.server.oauth.domain.OAuth2UserInfo;
import com.talkka.server.user.dto.UserDto;
import com.talkka.server.user.dto.UserRespDto;
import com.talkka.server.user.dto.UserUpdateDto;
import com.talkka.server.user.dto.UserUpdateReqDto;
import com.talkka.server.user.exception.DuplicatedNicknameException;
import com.talkka.server.user.exception.UserNotFoundException;
import com.talkka.server.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController implements UserApiDocs {
	private final UserService userService;

	@GetMapping("/{user_id}")
	@Secured("ADMIN")
	public ResponseEntity<?> getUser(
		@PathVariable("user_id") Long userId
	) {
		ResponseEntity<?> response;
		try {
			UserRespDto userRespDto = UserRespDto.of(userService.getUser(userId));
			response = ResponseEntity.ok(userRespDto);
		} catch (UserNotFoundException exception) {
			response = new ResponseEntity<>(ErrorRespDto.of(exception), HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@PutMapping("/{user_id}")
	@Secured("ADMIN")
	public ResponseEntity<?> updateUser(@PathVariable("user_id") Long userId,
		@RequestBody @Valid UserUpdateReqDto userUpdateReqDto) {
		ResponseEntity<?> response;
		try {
			UserUpdateDto userUpdateDto = UserUpdateDto.of(userId, userUpdateReqDto);
			UserRespDto userRespDto = UserRespDto.of(
				userService.updateUser(userUpdateDto));

			response = ResponseEntity.ok(userRespDto);
		} catch (InvalidTypeException | DuplicatedNicknameException | UserNotFoundException exception) {
			response = ResponseEntity.badRequest().body(ErrorRespDto.of(exception));
		}
		return response;
	}

	@DeleteMapping("/{user_id}")
	@Secured("ADMIN")
	public ResponseEntity<?> deleteUser(@PathVariable("user_id") Long userId) {
		ResponseEntity<?> response;
		try {
			Long deletedUserId = userService.deleteUser(userId);
			response = ResponseEntity.ok(deletedUserId);
		} catch (UserNotFoundException exception) {
			response = ResponseEntity.badRequest().body(ErrorRespDto.of(exception));
		}
		return response;
	}

	@GetMapping("/me")
	@Secured({"USER"})
	public ResponseEntity<?> getMe(@AuthenticationPrincipal OAuth2UserInfo userInfo) {
		ResponseEntity<?> response;
		try {
			UserRespDto userRespDto = UserRespDto.of(userService.getUser(userInfo.getUserId()));
			response = ResponseEntity.ok(userRespDto);
		} catch (UserNotFoundException exception) {
			response = new ResponseEntity<>("권한이 없습니다.", HttpStatus.UNAUTHORIZED);
		}
		return response;
	}

	@PutMapping("/me")
	@Secured({"USER"})
	public ResponseEntity<?> updateMe(@AuthenticationPrincipal OAuth2UserInfo userInfo,
		@RequestBody @Valid UserUpdateReqDto userUpdateReqDto) {
		ResponseEntity<?> response;
		try {
			UserUpdateDto userUpdateDto = UserUpdateDto.of(userInfo.getUserId(), userUpdateReqDto);
			UserDto userDto = userService.updateUser(userUpdateDto);
			UserRespDto userRespDto = UserRespDto.of(userDto);
			response = ResponseEntity.ok(userRespDto);
		} catch (InvalidTypeException | DuplicatedNicknameException exception) {
			response = ResponseEntity.badRequest().body(ErrorRespDto.of(exception));
		} catch (UserNotFoundException exception) {
			response = new ResponseEntity<>("권한이 없습니다.", HttpStatus.UNAUTHORIZED);
		}
		return response;
	}
}
