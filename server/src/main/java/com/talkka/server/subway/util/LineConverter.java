package com.talkka.server.subway.util;

import com.talkka.server.common.util.EnumCodeConverter;
import com.talkka.server.subway.enums.Line;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class LineConverter extends EnumCodeConverter<Line> {
	public LineConverter() {
		super(Line.class);
	}
}
