package com.talkka.server.review.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import com.talkka.server.review.vo.Rating;

public class RatingConverter implements Converter<Integer, Rating> {
	@Override
	public Rating convert(@NonNull Integer source) {
		return new Rating(source);
	}
}
