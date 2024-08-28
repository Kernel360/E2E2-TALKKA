package com.talkka.server.common.util;

import com.talkka.server.common.enums.TimeSlot;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TimeSlotTypeConverter extends EnumCodeConverter<TimeSlot> {
	public TimeSlotTypeConverter() {
		super(TimeSlot.class);
	}
}
