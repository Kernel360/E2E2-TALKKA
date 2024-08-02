package com.talkka.server.oauth.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.talkka.server.oauth.domain.AuthUserDto;
import com.talkka.server.oauth.domain.CustomUserPrincipal;
import com.talkka.server.user.dto.UserCreateDto;
import com.talkka.server.user.dto.UserDto;
import com.talkka.server.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final UserService userService;

	@GetMapping("/login")
	public String loginForm() {
		return "loginForm";
	}

	@GetMapping("/signUp")
	public String signUpForm(Model model, @AuthenticationPrincipal CustomUserPrincipal principal) {
		AuthUserDto userDto = principal.getUser();
		model.addAttribute("user", userDto);
		return "signUpForm";
	}

	@SuppressWarnings("checkstyle:WhitespaceAround")
	@PostMapping("/signUp")
	public String signUp(@RequestParam("nickname") String nickname,
		Model model,
		@AuthenticationPrincipal CustomUserPrincipal principal,
		HttpServletRequest request) {
		if (userService.isDuplicatedNickname(nickname)) {
			return "signUpForm";
		}
		AuthUserDto authUserDto = principal.getUser();
		UserCreateDto userCreateDto = UserCreateDto.builder()
			.name(authUserDto.getName())
			.email(authUserDto.getEmail())
			.oauthProvider(authUserDto.getProvider())
			.nickname(nickname)
			.accessToken(authUserDto.getAccessToken())
			.build();
		UserDto user = userService.createUser(userCreateDto);
		request.getSession().invalidate();
		return "redirect:/";
	}

	@GetMapping("/login/naver")
	public String loginWithNaver() {
		return "redirect:https://nid.naver.com/oauth2.0/authorize";
	}
}
