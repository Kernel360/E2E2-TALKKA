package com.talkka.server.bus.util;

import com.talkka.server.bus.enums.EndBus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EndBusConverter implements AttributeConverter<EndBus, String> {
	@Override
	public String convertToDatabaseColumn(EndBus endBus) {
		return endBus != null ? endBus.getCode() : null;
	}

	@Override
	public EndBus convertToEntityAttribute(String value) {
		return EndBus.fromCode(value);
	}
}
