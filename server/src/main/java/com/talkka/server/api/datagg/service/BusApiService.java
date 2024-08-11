package com.talkka.server.api.datagg.service;

import java.util.List;

import com.talkka.server.api.datagg.dto.BusLocationBodyDto;
import com.talkka.server.api.datagg.dto.BusRouteInfoBodyDto;
import com.talkka.server.api.datagg.dto.BusRouteSearchBodyDto;
import com.talkka.server.api.datagg.dto.BusRouteStationBodyDto;

public interface BusApiService {
	List<BusRouteSearchBodyDto> getSearchedRouteInfo(String routeName);

	List<BusRouteInfoBodyDto> getRouteInfo(String apiRouteId);

	List<BusRouteStationBodyDto> getRouteStationInfo(String apiRouteId);

	List<BusLocationBodyDto> getBusLocationInfo(String apiRouteId);

	// List<RouteBusStationArrivalInfoRespDto> getBusStationArrivalInfo(String routeId);
}
