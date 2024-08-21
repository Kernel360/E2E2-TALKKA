package com.talkka.server.oauth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talkka.server.oauth.enums.AuthRole;
import com.talkka.server.oauth.service.DevLoginService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DevLoginController {

	private final DevLoginService loginService;

	/*
	 * User 세션 생성 : localhost:8080/dev-login?authRole=user
	 * Admin 세션 생성 : localhost:8080/dev-login?authRole=admin
	 * */
	@GetMapping("/dev-login")
	public ResponseEntity<String> manualAuth(@RequestParam String authRole, HttpServletRequest request) {
		ResponseEntity<String> response;

		try {
			Authentication authentication = loginService.getAuthentication(AuthRole.valueOf(authRole.toUpperCase()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			HttpSession session = request.getSession(true);
			session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
				SecurityContextHolder.getContext());

			response = ResponseEntity.ok("Success Login " + authRole);
		} catch (IllegalArgumentException exception) {
			response = ResponseEntity.badRequest().body(exception.getMessage());
		}

		return response;
	}
}
