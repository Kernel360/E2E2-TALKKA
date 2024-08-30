package com.talkka.server.common.exception.handler;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.talkka.server.common.exception.handler.test_mocks.ControllerAdviceTestController;
import com.talkka.server.common.exception.handler.test_mocks.ControllerAdviceTestService;
import com.talkka.server.common.exception.http.BadRequestException;
import com.talkka.server.common.exception.http.NotFoundException;

//  NOTE: 테스트 코드 수정이 필요함.
@WebMvcTest(ControllerAdviceTestController.class)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(properties = "test.enabled=true")
class RestControllerAdviceTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ControllerAdviceTestService controllerAdviceTestService;

	@Test
	void testBadRequestException() throws Exception {
		given(controllerAdviceTestService.something())
			.willThrow(new BadRequestException("Bad Request"));
		this.mockMvc.perform(get("/test/controller-advice"))
			.andExpect(status().isBadRequest());
	}

	@Test
	void testNotFoundException() throws Exception {
		given(controllerAdviceTestService.something())
			.willThrow(new NotFoundException("Not Found"));
		this.mockMvc.perform(get("/test/controller-advice"))
			.andExpect(status().isNotFound());
	}
}