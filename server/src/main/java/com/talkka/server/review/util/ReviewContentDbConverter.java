package com.talkka.server.review.util;

import com.talkka.server.review.vo.ReviewContent;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ReviewContentDbConverter implements AttributeConverter<ReviewContent, String> {
	@Override
	public String convertToDatabaseColumn(ReviewContent reviewContent) {
		if (reviewContent == null) {
			return null;
		}
		return reviewContent.value();
	}

	@Override
	public ReviewContent convertToEntityAttribute(String s) {
		return new ReviewContent(s);
	}
}
