package com.talkka.server.common.exception.handler;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.talkka.server.common.enums.StatusCode;
import com.talkka.server.common.exception.CustomException;
import com.talkka.server.common.exception.handler.test_mocks.ControllerAdviceTestController;
import com.talkka.server.common.exception.handler.test_mocks.ControllerAdviceTestService;
import com.talkka.server.common.exception.http.BadRequestException;
import com.talkka.server.common.exception.http.NotFoundException;

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
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.statusCode").value(400))
			.andExpect(jsonPath("$.message").value("Bad Request"));
	}

	@Test
	void testNotFoundException() throws Exception {
		given(controllerAdviceTestService.something())
			.willThrow(new NotFoundException("Not Found"));
		this.mockMvc.perform(get("/test/controller-advice"))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.statusCode").value(404))
			.andExpect(jsonPath("$.message").value("Not Found"));
	}

	@Test
	void testDomainException() throws Exception {
		given(controllerAdviceTestService.something())
			.willThrow(new CustomException(HttpStatus.UNAUTHORIZED, StatusCode.DUPLICATED_NICKNAME) {
			});
		this.mockMvc.perform(get("/test/controller-advice"))
			.andExpect(status().isUnauthorized())
			.andExpect(jsonPath("$.statusCode").value(StatusCode.DUPLICATED_NICKNAME.getCode()))
			.andExpect(jsonPath("$.message").value(StatusCode.DUPLICATED_NICKNAME.getMessage()));
	}
}