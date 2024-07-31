package com.talkka.server.bus.util;

import com.talkka.server.bus.enums.DistrictCode;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DistrictCodeConverter implements AttributeConverter<DistrictCode, String> {
	@Override
	public String convertToDatabaseColumn(DistrictCode districtCode) {
		return districtCode != null ? districtCode.getCode() : null;
	}

	@Override
	public DistrictCode convertToEntityAttribute(String value) {
		return DistrictCode.fromCode(value);
	}
}