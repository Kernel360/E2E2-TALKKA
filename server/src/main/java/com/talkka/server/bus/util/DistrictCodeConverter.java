package com.talkka.server.bus.util;

import com.talkka.server.bus.enums.DistrictCode;
import com.talkka.server.common.util.EnumCodeConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DistrictCodeConverter extends EnumCodeConverter<DistrictCode> {
	public DistrictCodeConverter() {
		super(DistrictCode.class);
	}
}