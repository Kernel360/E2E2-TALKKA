package com.talkka.server.review.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import com.talkka.server.review.vo.ReviewContent;

public class ReviewContentConverter implements Converter<String, ReviewContent> {
	@Override
	public ReviewContent convert(@NonNull String source) {
		return new ReviewContent(source);
	}
}
