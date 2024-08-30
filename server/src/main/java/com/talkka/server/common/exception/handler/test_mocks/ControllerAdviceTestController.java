package com.talkka.server.common.exception.handler.test_mocks;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

// 해당 컨트롤러는 ControllerAdvice 테스트를 위한 컨트롤러입니다.
@RestController
@RequestMapping("/test")
@ConditionalOnProperty(prefix = "test", name = "enabled", havingValue = "true")
@RequiredArgsConstructor
public class ControllerAdviceTestController {
	private final ControllerAdviceTestService controllerAdviceTestService;

	@GetMapping("/controller-advice")
	public void exception() {
		controllerAdviceTestService.something();
	}
}
