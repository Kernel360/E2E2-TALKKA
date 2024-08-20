package com.talkka.server.bus.controller;

import static com.talkka.server.bus.BusTestFactory.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.talkka.server.bus.service.BusRouteService;

@ExtendWith(MockitoExtension.class)
public class BusRouteControllerTest {

	@InjectMocks
	private BusRouteController routeController;

	@Mock
	private BusRouteService routeService;

	@Nested
	@DisplayName("findByRouteId method")
	public class FindByRouteIdTest {
		@Test
		void routeId를_받아_해당_아이디의_노선을_조회한다() {
			// given
			Long id = 1L;
			var expected = ResponseEntity.ok(getBusRouteRespDto(id));
			given(routeService.getRouteById(anyLong())).willReturn(getBusRouteRespDto(id));
			// when
			var result = routeController.getRouteById(id);
			// then
			verify(routeService, times(1)).getRouteById(anyLong());
			Assertions.assertThat(result).isEqualTo(expected);
		}
	}

	@Nested
	@DisplayName("findByRouteId method")
	public class FindByRouteNameTest {
		@Test
		void routeName을_받아_해당_이름으로_시작하는_노선을_조회한다() {
			// given
			Long id1 = 1L;
			Long id2 = 2L;
			Long id3 = 3L;
			var expected = ResponseEntity.ok(
				List.of(
					getBusRouteRespDto(id1),
					getBusRouteRespDto(id2),
					getBusRouteRespDto(id3)
				)
			);
			given(routeService.getRoutesByRouteName(anyString())).willReturn(
				List.of(
					getBusRouteRespDto(id1),
					getBusRouteRespDto(id2),
					getBusRouteRespDto(id3)
				)
			);
			// when
			var result = routeController.getRoutes("7800");
			// then
			verify(routeService, times(1)).getRoutesByRouteName(anyString());
			Assertions.assertThat(result).isEqualTo(expected);
		}
	}
}
