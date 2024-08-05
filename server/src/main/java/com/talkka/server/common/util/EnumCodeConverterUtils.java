package com.talkka.server.common.util;

public class EnumCodeConverterUtils {
	public static <T extends Enum<T>> T fromCode(Class<T> enumType, String codeString) {
		for (T type : enumType.getEnumConstants()) {
			if (type.toString().equals(codeString)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid value: " + codeString);
	}

	public static <T extends Enum<T> & EnumCodeInterface> String toCode(T value) {
		return value.getCode();
	}
}
