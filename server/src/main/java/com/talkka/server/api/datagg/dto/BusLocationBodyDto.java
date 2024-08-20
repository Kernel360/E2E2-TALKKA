package com.talkka.server.api.datagg.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.talkka.server.bus.dao.BusLocationEntity;
import com.talkka.server.bus.enums.EndBus;
import com.talkka.server.bus.enums.LowPlate;
import com.talkka.server.bus.enums.PlateType;
import com.talkka.server.common.util.EnumCodeConverterUtils;

@JacksonXmlRootElement(localName = "busLocationList")
public record BusLocationBodyDto(String endBus, String lowPlate, String plateNo, String plateType,
								 Integer remainSeatCnt, String routeId, String stationId, Integer stationSeq) {

	public BusLocationEntity toEntity(int apiCallNo, LocalDateTime createdAt) {
		EndBus endBusEnum = EnumCodeConverterUtils.fromCode(EndBus.class, endBus);
		LowPlate lowPlateEnum = EnumCodeConverterUtils.fromCode(LowPlate.class, lowPlate);
		PlateType plateTypeEnum = EnumCodeConverterUtils.fromCode(PlateType.class, plateType);

		return BusLocationEntity.builder()
			.apiRouteId(routeId)
			.apiStationId(stationId)
			.stationSeq(stationSeq.shortValue())
			.endBus(endBusEnum)
			.lowPlate(lowPlateEnum)
			.plateNo(plateNo)
			.plateType(plateTypeEnum)
			.remainSeatCount(remainSeatCnt.shortValue())
			.apiCallNo(apiCallNo)
			.createdAt(createdAt)
			.build();
	}
}
