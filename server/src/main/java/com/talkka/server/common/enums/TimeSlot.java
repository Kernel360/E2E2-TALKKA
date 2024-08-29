package com.talkka.server.common.enums;

import java.time.LocalDateTime;

import com.talkka.server.common.exception.enums.InvalidTimeSlotEnumException;

public enum TimeSlot {
	T_00_00,
	T_00_30,
	T_01_00,
	T_01_30,
	T_02_00,
	T_02_30,
	T_03_00,
	T_03_30,
	T_04_00,
	T_04_30,
	T_05_00,
	T_05_30,
	T_06_00,
	T_06_30,
	T_07_00,
	T_07_30,
	T_08_00,
	T_08_30,
	T_09_00,
	T_09_30,
	T_10_00,
	T_10_30,
	T_11_00,
	T_11_30,
	T_12_00,
	T_12_30,
	T_13_00,
	T_13_30,
	T_14_00,
	T_14_30,
	T_15_00,
	T_15_30,
	T_16_00,
	T_16_30,
	T_17_00,
	T_17_30,
	T_18_00,
	T_18_30,
	T_19_00,
	T_19_30,
	T_20_00,
	T_20_30,
	T_21_00,
	T_21_30,
	T_22_00,
	T_22_30,
	T_23_00,
	T_23_30;

	TimeSlot() {
	}

	public static TimeSlot valueOfEnumString(String enumValue) {
		try {
			return TimeSlot.valueOf(enumValue);
		} catch (IllegalArgumentException exception) {
			throw new InvalidTimeSlotEnumException();
		}
	}

	public static TimeSlot of(LocalDateTime time) {
		int hour = time.getHour();
		int minute = time.getMinute();
		if (minute < 30) {
			return TimeSlot.valueOf("T_" + String.format("%02d", hour) + "_00");
		} else {
			return TimeSlot.valueOf("T_" + String.format("%02d", hour) + "_30");
		}
	}

	public LocalDateTime getDateTime() {
		String[] time = this.name().substring(2).split("_");
		LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();
		int month = now.getMonthValue();
		int day = now.getDayOfMonth();
		int hour = Integer.parseInt(time[0]);
		int minute = Integer.parseInt(time[1]);

		return LocalDateTime.of(year, month, day, hour, minute);
	}
}