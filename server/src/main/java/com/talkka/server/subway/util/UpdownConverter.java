package com.talkka.server.subway.util;

import com.talkka.server.common.util.EnumCodeConverter;
import com.talkka.server.subway.enums.Updown;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UpdownConverter extends EnumCodeConverter<Updown> {
	public UpdownConverter() {
		super(Updown.class);
	}
}
