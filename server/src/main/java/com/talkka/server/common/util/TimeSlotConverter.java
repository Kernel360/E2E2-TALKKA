package com.talkka.server.common.util;

import com.talkka.server.common.enums.TimeSlot;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TimeSlotConverter extends EnumCodeConverter<TimeSlot> {
	public TimeSlotConverter() {
		super(TimeSlot.class);
	}
}
