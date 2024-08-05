package com.talkka.server.bus.util;

import com.talkka.server.bus.enums.EndBus;
import com.talkka.server.common.util.EnumCodeConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EndBusConverter extends EnumCodeConverter<EndBus> {
	public EndBusConverter() {
		super(EndBus.class);
	}
}
