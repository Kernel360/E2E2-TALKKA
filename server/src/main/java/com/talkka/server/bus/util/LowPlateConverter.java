package com.talkka.server.bus.util;

import com.talkka.server.bus.enums.LowPlate;
import com.talkka.server.common.util.EnumCodeConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class LowPlateConverter extends EnumCodeConverter<LowPlate> {
	public LowPlateConverter() {
		super(LowPlate.class);
	}
}
