package com.talkka.server.subway.util;

import com.talkka.server.common.util.EnumCodeConverter;
import com.talkka.server.subway.enums.Express;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ExpressConverter extends EnumCodeConverter<Express> {
	public ExpressConverter() {
		super(Express.class);
	}
}
