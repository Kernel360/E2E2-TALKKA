package com.talkka.server.subway.util;

import com.talkka.server.common.util.EnumCodeConverter;
import com.talkka.server.subway.enums.DayType;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DayTypeConverter extends EnumCodeConverter<DayType> {
	public DayTypeConverter() {
		super(DayType.class);
	}
}
