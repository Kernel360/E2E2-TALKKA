package com.talkka.server.subway.util;

import com.talkka.server.common.util.EnumCodeConverter;
import com.talkka.server.subway.enums.SubwayTimeSlot;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SubwayTimeSlotConverter extends EnumCodeConverter<SubwayTimeSlot> {
	public SubwayTimeSlotConverter() {
		super(SubwayTimeSlot.class);
	}
}
