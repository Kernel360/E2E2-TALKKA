package com.talkka.server.review.util;

import com.talkka.server.common.util.EnumCodeConverter;
import com.talkka.server.review.enums.BusTimeSlot;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BusTimeSlotConverter extends EnumCodeConverter<BusTimeSlot> {
	public BusTimeSlotConverter() {
		super(BusTimeSlot.class);
	}
}
