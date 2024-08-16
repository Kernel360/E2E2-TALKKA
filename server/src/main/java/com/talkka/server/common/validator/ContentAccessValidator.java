package com.talkka.server.common.validator;

import org.springframework.stereotype.Component;

import com.talkka.server.review.exception.ContentAccessException;
import com.talkka.server.user.enums.Grade;

@Component
public class ContentAccessValidator {
	public boolean validateOwnerContentAccess(Long userId, Grade grade, Long contentUserId) throws
		ContentAccessException {
		if (grade == Grade.ADMIN || userId.equals(contentUserId)) {
			return true;
		}
		throw new ContentAccessException();
	}
}
