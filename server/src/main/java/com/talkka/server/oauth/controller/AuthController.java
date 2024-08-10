package com.talkka.server.oauth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talkka.server.common.dto.ApiRespDto;
import com.talkka.server.oauth.domain.OAuth2UserInfo;
import com.talkka.server.user.dto.UserCreateDto;
import com.talkka.server.user.dto.UserCreateReqDto;
import com.talkka.server.user.service.UserService;

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
	public ResponseEntity<ApiRespDto<Void>> register(
		@AuthenticationPrincipal OAuth2UserInfo userInfo,
		@RequestBody @Valid UserCreateReqDto userCreateReqDto,
		HttpServletRequest request) {
		String nickname = userCreateReqDto.nickname();
		if (userService.isDuplicatedNickname(nickname)) {
			return ResponseEntity.badRequest().body(
				ApiRespDto.<Void>builder()
					.statusCode(400)
					.message("중복된 닉네임입니다.")
					.build()
			);
		}

		UserCreateDto userCreateDto = UserCreateDto.builder()
			.name(userInfo.getName())
			.email(userInfo.getEmail())
			.oauthProvider(userInfo.getProvider())
			.nickname(nickname)
			.accessToken(userInfo.getAccessToken())
			.build();
		userService.createUser(userCreateDto);
		request.getSession().invalidate();
		return ResponseEntity.ok(
			ApiRespDto.<Void>builder()
				.statusCode(200)
				.message("회원가입이 완료되었습니다.")
				.build()
		);
	}
}
