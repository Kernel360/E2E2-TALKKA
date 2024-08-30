package com.talkka.server.common.util;

import com.talkka.server.common.exception.enums.InvalidEnumCodeException;

public class EnumCodeConverterUtils {
	public static <T extends Enum<T> & EnumCodeInterface> T fromCode(Class<T> enumType, String codeString) {
		for (T type : enumType.getEnumConstants()) {
			if (type.getCode().equals(codeString)) {
				return type;
			}
		}
		throw new InvalidEnumCodeException(enumType, codeString);
	}

	public static <T extends Enum<T> & EnumCodeInterface> String toCode(T value) {
		return value.getCode();
	}
}
