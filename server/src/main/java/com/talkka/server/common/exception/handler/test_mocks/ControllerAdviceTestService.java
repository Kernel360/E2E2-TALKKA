package com.talkka.server.common.exception.handler.test_mocks;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

// 해당 서비스는 ControllerAdvice 테스트를 위한 서비스입니다.
@Service
@ConditionalOnProperty(prefix = "test", name = "enabled", havingValue = "true")
public class ControllerAdviceTestService {
	public Object something() {
		return new Object();
	}
}
