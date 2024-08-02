package com.talkka.server.user.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.talkka.server.review.dao.BusReviewEntity;
import com.talkka.server.user.dao.UserEntity;
import com.talkka.server.user.enums.Grade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private Long userId;
	private String nickname;
	private String oauthProvider;
	private String accessToken;
	private Grade grade;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private List<BusReviewEntity> busReviews;

	public static UserDto of(UserEntity userEntity) {
		return UserDto.builder()
			.userId(userEntity.getUserId())
			.nickname(userEntity.getNickname())
			.oauthProvider(userEntity.getOauthProvider())
			.accessToken(userEntity.getAccessToken())
			.grade(userEntity.getGrade())
			.createdAt(userEntity.getCreatedAt())
			.updatedAt(userEntity.getUpdatedAt())
			.busReviews(userEntity.getBusReviews())
			.build();
	}

	public UserEntity toEntity() {
		return UserEntity.builder()
			.userId(userId)
			.nickname(nickname)
			.oauthProvider(oauthProvider)
			.accessToken(accessToken)
			.grade(grade)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.busReviews(busReviews)
			.build();
	}
}
