package com.talkka.server.common.util;

public class EnumCodeConverterUtils {
	public static <T extends Enum<T>> T fromCode(Class<T> enumType, String value) {
		for (T type : enumType.getEnumConstants()) {
			if (type.toString().equals(value)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid value: " + value);
	}

	public static <T extends Enum<T> & EnumCodeInterface> String toCode(T value) {
		return value.getCode();
	}
}
