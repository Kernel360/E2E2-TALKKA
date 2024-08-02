package com.talkka.server.oauth.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.talkka.server.oauth.domain.OAuth2UserInfo;
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
	public String signUpForm(Model model, @AuthenticationPrincipal OAuth2UserInfo principal) {
		model.addAttribute("name", principal.getName());
		model.addAttribute("email", principal.getEmail());
		return "signUpForm";
	}

	@SuppressWarnings("checkstyle:WhitespaceAround")
	@PostMapping("/signUp")
	public String signUp(@RequestParam("nickname") String nickname,
		Model model,
		@AuthenticationPrincipal OAuth2UserInfo principal,
		HttpServletRequest request) {
		if (userService.isDuplicatedNickname(nickname)) {
			return "signUpForm";
		}
		UserCreateDto userCreateDto = UserCreateDto.builder()
			.name(principal.getName())
			.email(principal.getEmail())
			.oauthProvider(principal.getProvider())
			.nickname(nickname)
			.accessToken("testToken")
			.build();
		UserDto user = userService.createUser(userCreateDto);
		request.getSession().invalidate();
		return "redirect:/oauth2/authorization/naver";
	}

	@GetMapping("/login/naver")
	public String loginWithNaver() {
		return "redirect:https://nid.naver.com/oauth2.0/authorize";
	}
}
