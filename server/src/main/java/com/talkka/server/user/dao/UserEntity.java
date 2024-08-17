package com.talkka.server.user.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.talkka.server.bookmark.dao.BookmarkEntity;
import com.talkka.server.review.dao.BusReviewEntity;
import com.talkka.server.user.enums.Grade;
import com.talkka.server.user.util.EmailDbConverter;
import com.talkka.server.user.util.NicknameDbConverter;
import com.talkka.server.user.vo.Email;
import com.talkka.server.user.vo.Nickname;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "users")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "email", length = 30, nullable = false)
	@Convert(converter = EmailDbConverter.class)
	private Email email;

	@Column(name = "nickname", length = 50, nullable = false)
	@Convert(converter = NicknameDbConverter.class)
	private Nickname nickname;

	@Column(name = "oauth_provider", length = 30, nullable = false, updatable = false)
	private String oauthProvider;

	@Column(name = "access_token", length = 255, nullable = false)
	private String accessToken;

	@Column(name = "grade", length = 20, nullable = true)
	@Enumerated(EnumType.STRING)
	private Grade grade;

	@CreatedDate
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "writer")
	@ToString.Exclude
	private List<BusReviewEntity> busReviews;

	@OneToMany(mappedBy = "user")
	@ToString.Exclude
	private List<BookmarkEntity> bookmarks;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserEntity that = (UserEntity)o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	public void updateUser(Nickname nickname) {
		this.nickname = nickname;
	}
}
