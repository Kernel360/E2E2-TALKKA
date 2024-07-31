package com.talkka.server.bus.util;

import com.talkka.server.bus.enums.BusRouteType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BusRoutTypeConverter implements AttributeConverter<BusRouteType, String> {
	@Override
	public String convertToDatabaseColumn(BusRouteType busRouteType) {
		return busRouteType != null ? busRouteType.getCode() : null;
	}

	@Override
	public BusRouteType convertToEntityAttribute(String value) {
		return BusRouteType.fromCode(value);
	}
}
