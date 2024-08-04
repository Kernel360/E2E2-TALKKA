package com.talkka.server.user.enums;

public enum Grade {
	ADMIN, USER;

	public static Grade fromString(String value) {
		for (Grade grade : Grade.values()) {
			if (grade.name().equals(value)) {
				return grade;
			}
		}
		throw new IllegalArgumentException("Invalid value: " + value);
	}
}
