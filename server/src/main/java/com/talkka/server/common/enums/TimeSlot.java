package com.talkka.server.common.enums;

import java.time.LocalDateTime;

import com.talkka.server.common.exception.enums.InvalidTimeSlotEnumException;
import com.talkka.server.common.util.EnumCodeInterface;

public enum TimeSlot implements EnumCodeInterface {
	T_00_00(0),
	T_00_30(1),
	T_01_00(2),
	T_01_30(3),
	T_02_00(4),
	T_02_30(5),
	T_03_00(6),
	T_03_30(7),
	T_04_00(8),
	T_04_30(9),
	T_05_00(10),
	T_05_30(11),
	T_06_00(12),
	T_06_30(13),
	T_07_00(14),
	T_07_30(15),
	T_08_00(16),
	T_08_30(17),
	T_09_00(18),
	T_09_30(19),
	T_10_00(20),
	T_10_30(21),
	T_11_00(22),
	T_11_30(23),
	T_12_00(24),
	T_12_30(25),
	T_13_00(26),
	T_13_30(27),
	T_14_00(28),
	T_14_30(29),
	T_15_00(30),
	T_15_30(31),
	T_16_00(32),
	T_16_30(33),
	T_17_00(34),
	T_17_30(35),
	T_18_00(36),
	T_18_30(37),
	T_19_00(38),
	T_19_30(39),
	T_20_00(40),
	T_20_30(41),
	T_21_00(42),
	T_21_30(43),
	T_22_00(44),
	T_22_30(45),
	T_23_00(46),
	T_23_30(47);

	private final int code;

	TimeSlot(int code) {
		this.code = code;
	}

	@Override
	public String getCode() {
		return String.valueOf(this.code);
	}

	public int getCodeValue() {
		return this.code;
	}

	public static TimeSlot valueOfEnumString(String enumValue) {
		try {
			return TimeSlot.valueOf(enumValue);
		} catch (IllegalArgumentException exception) {
			throw new InvalidTimeSlotEnumException();
		}
	}

	public static TimeSlot fromCode(int code) {
		for (TimeSlot timeSlot : TimeSlot.values()) {
			if (timeSlot.code == code) {
				return timeSlot;
			}
		}
		throw new InvalidTimeSlotEnumException();
	}

	public static TimeSlot fromLocalDateTime(LocalDateTime time) {
		int code = 2 * time.getHour() + (time.getMinute() < 30 ? 0 : 1);
		return TimeSlot.fromCode(code);
	}
}
