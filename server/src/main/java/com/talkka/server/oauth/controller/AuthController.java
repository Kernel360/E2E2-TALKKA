package com.talkka.server.oauth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talkka.server.common.exception.InvalidTypeException;
import com.talkka.server.oauth.domain.OAuth2UserInfo;
import com.talkka.server.user.dto.UserCreateDto;
import com.talkka.server.user.dto.UserCreateReqDto;
import com.talkka.server.user.exception.DuplicatedNicknameException;
import com.talkka.server.user.service.UserService;
import com.talkka.server.user.vo.Email;
import com.talkka.server.user.vo.Nickname;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final UserService userService;

	@PostMapping("/register")
	@Secured("UNREGISTERED")
	public ResponseEntity<?> register(
		@AuthenticationPrincipal OAuth2UserInfo userInfo,
		@RequestBody @Valid UserCreateReqDto userCreateReqDto,
		HttpServletRequest request) {
		ResponseEntity<?> response;

		try {
			Nickname nickname = new Nickname(userCreateReqDto.nickname());
			Email email = new Email(userInfo.getEmail());
			UserCreateDto userCreateDto = UserCreateDto.builder()
				.name(userInfo.getName())
				.email(email)
				.oauthProvider(userInfo.getProvider())
				.nickname(nickname)
				.accessToken(userInfo.getAccessToken())
				.build();
			var result = userService.createUser(userCreateDto);

			// invalidate UNREGISTERED_USER session
			request.getSession().invalidate();

			response = ResponseEntity.ok().build();
		} catch (InvalidTypeException | DuplicatedNicknameException exception) {
			response = ResponseEntity.badRequest().body(exception.getMessage());
		}
		return response;
	}
}
