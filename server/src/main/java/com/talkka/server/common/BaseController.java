package com.talkka.server.common;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.talkka.server.oauth.domain.OAuth2UserInfo;

// 인증 테스트를 위한 임시 컨트롤러
@Controller
public class BaseController {
	@GetMapping("/")
	public String authIndex(Model model, @AuthenticationPrincipal OAuth2UserInfo userInfo) {
		model.addAttribute("name", userInfo.getName());
		model.addAttribute("email", userInfo.getEmail());
		model.addAttribute("nickname", userInfo.getNickName());
		model.addAttribute("oauth2Id", userInfo.getOAuth2Id());
		model.addAttribute("provider", userInfo.getProvider());
		model.addAttribute("accessToken", userInfo.getAccessToken());
		return "index";
	}
}
