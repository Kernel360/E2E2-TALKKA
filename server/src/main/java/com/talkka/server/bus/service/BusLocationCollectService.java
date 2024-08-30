package com.talkka.server.bus.service;

public interface BusLocationCollectService {
	void collectLocations();

	void collectLocationsByRouteId(String apiRouteId);
}
