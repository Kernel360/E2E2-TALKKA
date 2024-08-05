package com.talkka.server.bus.util;

import com.talkka.server.bus.enums.BusRouteType;
import com.talkka.server.common.util.EnumCodeConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BusRouteTypeConverter extends EnumCodeConverter<BusRouteType> {
	public BusRouteTypeConverter() {
		super(BusRouteType.class);
	}
}
