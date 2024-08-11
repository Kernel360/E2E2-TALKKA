package com.talkka.server.api.datagg.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BusApiServiceImplTest {
	@Autowired
	private BusApiService busApiService;

	@Nested
	@DisplayName("getSearchedRouteInfo method")
	public class GetSearchedRouteInfo {
		@Test
		void routeName을_검색하면_해당_키워드를_가진_버스_노선_정보를_리스트로_반환한다() {
			// given
			String routeName = "78";

			// when
			var result = busApiService.getSearchedRouteInfo(routeName);

			// then
			assertNotNull(result);
			assertFalse(result.isEmpty());
			for (var dto : result) {
				assertThat(dto.routeName()).contains(routeName);
			}
		}
	}

	@Nested
	@DisplayName("getRouteInfo method")
	public class GetRouteInfo {
		@Test
		void routeId를_검색하면_해당_버스_노선_정보를_리스트로_반환한다() {
			// given
			String routeId = "200000150";

			// when
			var result = busApiService.getRouteInfo(routeId);

			// then
			assertNotNull(result);
			assertFalse(result.isEmpty());
			for (var dto : result) {
				assertThat(dto.routeId()).isEqualTo(Long.parseLong(routeId));
			}
		}
	}

	@Nested
	@DisplayName("getRouteStationInfo method")
	public class GetRouteStationInfo {
		@Test
		void routeId를_검색하면_해당_버스_노선_정류장_정보를_리스트로_반환한다() {
			// given
			String routeId = "200000150";

			// when
			var result = busApiService.getRouteStationInfo(routeId);

			// then
			assertNotNull(result);
			assertFalse(result.isEmpty());
			System.out.println(result);
		}
	}

	@Nested
	@DisplayName("getBusLocationInfo method")
	public class GetBusLocationInfo {
		@Test
		void routeId를_검색하면_해당_버스_위치_정보를_리스트로_반환한다() {
			// given
			String routeId = "200000150";

			// when
			var result = busApiService.getBusLocationInfo(routeId);

			// then
			assertNotNull(result);
			assertFalse(result.isEmpty());
			System.out.println(result);
		}
	}
}