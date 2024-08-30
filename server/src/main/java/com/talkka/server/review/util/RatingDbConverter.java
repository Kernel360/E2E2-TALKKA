package com.talkka.server.review.util;

import com.talkka.server.review.vo.Rating;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RatingDbConverter implements AttributeConverter<Rating, Integer> {
	@Override
	public Integer convertToDatabaseColumn(Rating rating) {
		return rating.value();
	}

	@Override
	public Rating convertToEntityAttribute(Integer integer) {
		return new Rating(integer);
	}
}
