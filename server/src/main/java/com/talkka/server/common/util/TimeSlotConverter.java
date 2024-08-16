package com.talkka.server.common.util;

import com.talkka.server.common.enums.TimeSlot;

import jakarta.persistence.Converter;

@Deprecated // 더 이상 Code로 DB에 저장하지 않습니다.
@Converter(autoApply = true)
public class TimeSlotConverter extends EnumCodeConverter<TimeSlot> {
	public TimeSlotConverter() {
		super(TimeSlot.class);
	}
}
