package com.talkka.server.api.datagg.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.talkka.server.api.core.exception.ApiClientException;
import com.talkka.server.api.datagg.config.BusApiKeyProperty;
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
	private final BusApiKeyProperty busApiKeyProperty;
	private final RestTemplate restTemplate = new RestTemplate();
	private static final String host = "apis.data.go.kr";

	@Override
	public List<BusRouteSearchBodyDto> getSearchedRouteInfo(String keyword) throws ApiClientException {
		final String path = "/6410000/busrouteservice/getBusRouteList";
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("keyword", keyword);
		try {
			URI uri = this.getOpenApiUri(path, params);
			ResponseEntity<BusRouteSearchRespDto> resp = restTemplate.getForEntity(uri, BusRouteSearchRespDto.class);
			var body = resp.getBody().msgBody();
			if (body == null) {
				throw new ApiClientException("결과가 없습니다.");
			}
			return body;
		} catch (Exception exception) {
			throw new ApiClientException(exception.getMessage());
		}
	}

	@Override
	public List<BusRouteInfoBodyDto> getRouteInfo(String apiRouteId) throws ApiClientException {
		final String path = "/6410000/busrouteservice/getBusRouteInfoItem";
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("routeId", apiRouteId);
		try {
			URI uri = this.getOpenApiUri(path, params);
			ResponseEntity<BusRouteInfoRespDto> resp = restTemplate.getForEntity(uri, BusRouteInfoRespDto.class);
			var body = resp.getBody().msgBody();
			if (body == null) {
				throw new ApiClientException("결과가 없습니다.");
			}
			return body;
		} catch (Exception exception) {
			throw new ApiClientException(exception.getMessage());
		}
	}

	@Override
	public List<BusRouteStationBodyDto> getRouteStationInfo(String apiRouteId) throws ApiClientException {
		final String path = "/6410000/busrouteservice/getBusRouteStationList";
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("routeId", apiRouteId);
		try {
			URI uri = this.getOpenApiUri(path, params);
			ResponseEntity<BusRouteStationRespDto> resp = restTemplate.getForEntity(uri, BusRouteStationRespDto.class);
			var body = resp.getBody().msgBody();
			if (body == null) {
				throw new ApiClientException("결과가 없습니다.");
			}
			return body;
		} catch (Exception exception) {
			throw new ApiClientException(exception.getMessage());
		}
	}

	@Override
	public List<BusLocationBodyDto> getBusLocationInfo(String apiRouteId) throws ApiClientException {
		final String path = "/6410000/buslocationservice/getBusLocationList";
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("routeId", apiRouteId);
		try {
			URI uri = this.getOpenApiUri(path, params);
			ResponseEntity<BusLocationRespDto> resp = restTemplate.getForEntity(uri, BusLocationRespDto.class);
			var body = resp.getBody().msgBody();
			if (body == null) {
				throw new ApiClientException("결과가 없습니다.");
			}
			return body;
		} catch (Exception exception) {
			throw new ApiClientException(exception.getMessage());
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
			.queryParam("serviceKey", this.busApiKeyProperty.getApiKey())
			.queryParams(params)
			.build();
	}
}
