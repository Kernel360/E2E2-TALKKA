package com.talkka.server.bus.util;

import com.talkka.server.bus.enums.PlateType;
import com.talkka.server.common.util.EnumCodeConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PlateTypeConverter extends EnumCodeConverter<PlateType> {
	public PlateTypeConverter() {
		super(PlateType.class);
	}
}
