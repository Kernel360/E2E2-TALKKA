package com.talkka.server.bus.util;

import com.talkka.server.bus.enums.CenterStation;
import com.talkka.server.common.util.EnumCodeConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CenterStationConverter extends EnumCodeConverter<CenterStation> {
	public CenterStationConverter() {
		super(CenterStation.class);
	}
}
