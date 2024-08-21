package com.talkka.server.common.validator;

import org.springframework.stereotype.Component;

import com.talkka.server.oauth.enums.AuthRole;
import com.talkka.server.review.exception.ContentAccessException;

@Component
public class ContentAccessValidator {
	public boolean validateOwnerContentAccess(Long userId, AuthRole authRole, Long contentUserId) throws
		ContentAccessException {
		if (authRole == AuthRole.ADMIN || userId.equals(contentUserId)) {
			return true;
		}
		throw new ContentAccessException();
	}
}
