package com.talkka.server.api.datagg.service;

import java.util.List;
import java.util.Optional;

import com.talkka.server.api.core.exception.ApiClientException;
import com.talkka.server.api.datagg.dto.BusArrivalBodyDto;
import com.talkka.server.api.datagg.dto.BusLocationBodyDto;
import com.talkka.server.api.datagg.dto.BusRouteInfoBodyDto;
import com.talkka.server.api.datagg.dto.BusRouteSearchBodyDto;
import com.talkka.server.api.datagg.dto.BusRouteStationBodyDto;

public interface BusApiService {
	List<BusRouteSearchBodyDto> getSearchedRouteInfo(String routeName) throws ApiClientException;

	List<BusRouteInfoBodyDto> getRouteInfo(String apiRouteId) throws ApiClientException;

	List<BusRouteStationBodyDto> getRouteStationInfo(String apiRouteId) throws ApiClientException;

	List<BusLocationBodyDto> getBusLocationInfo(String apiRouteId) throws ApiClientException;

	Optional<BusArrivalBodyDto> getBusArrival(String apiRouteId, String apiStationId) throws
		ApiClientException;
}
