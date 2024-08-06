package com.talkka.server.review.util;

import com.talkka.server.common.util.EnumCodeConverter;
import com.talkka.server.review.enums.TimeSlot;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TimeSlotConverter extends EnumCodeConverter<TimeSlot> {
	public TimeSlotConverter() {
		super(TimeSlot.class);
	}
}
