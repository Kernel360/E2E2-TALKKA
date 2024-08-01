package com.talkka.server.bus.util;

import com.talkka.server.bus.enums.TurnStation;
import com.talkka.server.common.util.EnumCodeConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TurnStationConverter extends EnumCodeConverter<TurnStation> {
	public TurnStationConverter() {
		super(TurnStation.class);
	}
}
