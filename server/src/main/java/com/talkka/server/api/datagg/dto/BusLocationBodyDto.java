package com.talkka.server.api.datagg.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "busLocationList")
public record BusLocationBodyDto(String endBus, String lowPlate, String plateNo, String plateType,
								 Integer remainSeatCnt, Long routeId, Long stationId, Integer stationSeq) {
}
