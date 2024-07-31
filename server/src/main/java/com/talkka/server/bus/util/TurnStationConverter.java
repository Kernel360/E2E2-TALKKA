package com.talkka.server.bus.util;

import com.talkka.server.bus.enums.TurnStation;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TurnStationConverter implements AttributeConverter<TurnStation, String> {
	@Override
	public String convertToDatabaseColumn(TurnStation turnStation) {
		return turnStation != null ? turnStation.getCode() : null;
	}

	@Override
	public TurnStation convertToEntityAttribute(String s) {
		return TurnStation.fromCode(s);
	}
}
