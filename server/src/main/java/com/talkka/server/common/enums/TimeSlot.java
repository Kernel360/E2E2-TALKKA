package com.talkka.server.common.enums;

import com.talkka.server.common.exception.enums.InvalidTimeSlotEnumException;
import com.talkka.server.common.util.EnumCodeInterface;

import lombok.Getter;

@Getter
public enum TimeSlot implements EnumCodeInterface {
	T_00_00("00:00", "0"),
	T_00_30("00:30", "1"),
	T_01_00("01:00", "2"),
	T_01_30("01:30", "3"),
	T_02_00("02:00", "4"),
	T_02_30("02:30", "5"),
	T_03_00("03:00", "6"),
	T_03_30("03:30", "7"),
	T_04_00("04:00", "8"),
	T_04_30("04:30", "9"),
	T_05_00("05:00", "10"),
	T_05_30("05:30", "11"),
	T_06_00("06:00", "12"),
	T_06_30("06:30", "13"),
	T_07_00("07:00", "14"),
	T_07_30("07:30", "15"),
	T_08_00("08:00", "16"),
	T_08_30("08:30", "17"),
	T_09_00("09:00", "18"),
	T_09_30("09:30", "19"),
	T_10_00("10:00", "20"),
	T_10_30("10:30", "21"),
	T_11_00("11:00", "22"),
	T_11_30("11:30", "23"),
	T_12_00("12:00", "24"),
	T_12_30("12:30", "25"),
	T_13_00("13:00", "26"),
	T_13_30("13:30", "27"),
	T_14_00("14:00", "28"),
	T_14_30("14:30", "29"),
	T_15_00("15:00", "30"),
	T_15_30("15:30", "31"),
	T_16_00("16:00", "32"),
	T_16_30("16:30", "33"),
	T_17_00("17:00", "34"),
	T_17_30("17:30", "35"),
	T_18_00("18:00", "36"),
	T_18_30("18:30", "37"),
	T_19_00("19:00", "38"),
	T_19_30("19:30", "39"),
	T_20_00("20:00", "40"),
	T_20_30("20:30", "41"),
	T_21_00("21:00", "42"),
	T_21_30("21:30", "43"),
	T_22_00("22:00", "44"),
	T_22_30("22:30", "45"),
	T_23_00("23:00", "46"),
	T_23_30("23:30", "47");

	private final String timeSlot;
	private final String code;

	TimeSlot(String timeSlot, String code) {
		this.timeSlot = timeSlot;
		this.code = code;
	}

	public static TimeSlot valueOfEnumString(String enumValue) {
		try {
			return TimeSlot.valueOf(enumValue);
		} catch (IllegalArgumentException exception) {
			throw new InvalidTimeSlotEnumException();
		}
	}
}
