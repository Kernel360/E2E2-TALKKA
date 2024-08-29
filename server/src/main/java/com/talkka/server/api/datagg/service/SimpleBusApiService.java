package com.talkka.server.api.datagg.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.talkka.server.api.core.config.ApiKeyProvider;
import com.talkka.server.api.core.exception.ApiClientException;
import com.talkka.server.api.datagg.dto.BusArrivalBodyDto;
import com.talkka.server.api.datagg.dto.BusArrivalRespDto;
import com.talkka.server.api.datagg.dto.BusLocationBodyDto;
import com.talkka.server.api.datagg.dto.BusLocationRespDto;
import com.talkka.server.api.datagg.dto.BusRouteInfoBodyDto;
import com.talkka.server.api.datagg.dto.BusRouteInfoRespDto;
import com.talkka.server.api.datagg.dto.BusRouteSearchBodyDto;
import com.talkka.server.api.datagg.dto.BusRouteSearchRespDto;
import com.talkka.server.api.datagg.dto.BusRouteStationBodyDto;
import com.talkka.server.api.datagg.dto.BusRouteStationRespDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SimpleBusApiService implements BusApiService {
	private static final Logger log = LoggerFactory.getLogger(SimpleBusApiService.class);
	private final ApiKeyProvider apiKeyProvider;
	private final RestTemplate restTemplate = new RestTemplate();
	private static final String host = "apis.data.go.kr";

	@Override
	public List<BusRouteSearchBodyDto> getSearchedRouteInfo(String keyword) throws ApiClientException {
		final String path = "/6410000/busrouteservice/getBusRouteList";
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("keyword", keyword);
		try {
			ResponseEntity<BusRouteSearchRespDto> resp = apiCallWithRetry(path, params, BusRouteSearchRespDto.class);
			return resp.getBody().msgBody();
		} catch (RestClientException exception) {
			throw new ApiClientException("결과가 없습니다.");
		}
	}

	@Override
	public List<BusRouteInfoBodyDto> getRouteInfo(String apiRouteId) throws ApiClientException {
		final String path = "/6410000/busrouteservice/getBusRouteInfoItem";
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("routeId", apiRouteId);
		try {
			ResponseEntity<BusRouteInfoRespDto> resp = apiCallWithRetry(path, params, BusRouteInfoRespDto.class);
			return resp.getBody().msgBody();
		} catch (RestClientException exception) {
			throw new ApiClientException("결과가 없습니다.");
		}
	}

	@Override
	public List<BusRouteStationBodyDto> getRouteStationInfo(String apiRouteId) throws ApiClientException {
		final String path = "/6410000/busrouteservice/getBusRouteStationList";
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("routeId", apiRouteId);
		try {
			ResponseEntity<BusRouteStationRespDto> resp = apiCallWithRetry(path, params, BusRouteStationRespDto.class);
			return resp.getBody().msgBody();
		} catch (RestClientException exception) {
			throw new ApiClientException("결과가 없습니다.");
		}
	}

	@Override
	public List<BusLocationBodyDto> getBusLocationInfo(String apiRouteId) throws ApiClientException {
		final String path = "/6410000/buslocationservice/getBusLocationList";
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("routeId", apiRouteId);
		try {
			ResponseEntity<BusLocationRespDto> resp = apiCallWithRetry(path, params, BusLocationRespDto.class);
			return resp.getBody().msgBody();
		} catch (RestClientException exception) {
			throw new ApiClientException("결과가 없습니다.");
		}
	}

	@Override
	public Optional<BusArrivalBodyDto> getBusArrival(String apiRouteId, String apiStationId) throws
		ApiClientException {
		final String path = "/6410000/busarrivalservice/getBusArrivalItem";
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("routeId", apiRouteId);
		params.add("stationId", apiStationId);
		try {
			URI uri = this.getOpenApiUri(path, params);
			ResponseEntity<BusArrivalRespDto> resp = restTemplate.getForEntity(uri, BusArrivalRespDto.class);
			var body = resp.getBody().msgBody();
			if (body == null || body.isEmpty()) {
				return Optional.empty();
			}
			return Optional.of(body.get(0));
		} catch (Exception exception) {
			throw new ApiClientException(exception.getMessage());
		}
	}

	private URI getOpenApiUri(String path, MultiValueMap<String, String> params) {
		final var builder = new DefaultUriBuilderFactory();
		builder.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
		return builder.builder()
			.scheme("https")
			.host(host)
			.path(path)
			.queryParam("serviceKey", this.apiKeyProvider.getApiKey(path))
			.queryParams(params)
			.build();
	}

	// 리트라이 로직을 포함한 api call
	private <T> ResponseEntity<T> apiCallWithRetry(String path, MultiValueMap<String, String> params,
		Class<T> type) throws RestClientException {

		final int MAX_ATTEMPTS = 10;
		final int RETRY_INTERVAL = 200;

		// 이후 bean 으로 등록하는것 고려
		RetryTemplate retryTemplate = new RetryTemplate();

		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
		retryPolicy.setMaxAttempts(MAX_ATTEMPTS);
		retryTemplate.setRetryPolicy(retryPolicy);

		FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
		backOffPolicy.setBackOffPeriod(RETRY_INTERVAL);
		retryTemplate.setBackOffPolicy(backOffPolicy);

		// 재시도마다 새로운 api key 로 시도
		// 파싱 실패시 RestClientException 터트림
		return retryTemplate.execute(context -> {
			// 재시도마다 새로운 api key 로 시도
			URI uri = this.getOpenApiUri(path, params);
			return restTemplate.getForEntity(uri, type); // 파싱 실패시 RestClientException 터트림
		});
	}
}
