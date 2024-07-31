package com.talkka.server.user.dao;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", columnDefinition = "bigint(32)", nullable = false, updatable = false)
	private Long userId;

	@Column(name = "nickname", columnDefinition = "varchar(50)", nullable = false)
	private String nickname;

	@Column(name = "oauth_provider", columnDefinition = "varchar(30)", nullable = false, updatable = false)
	private String oauthProvider;

	@Column(name = "access_token", columnDefinition = "varchar(255)", nullable = false)
	private String accessToken;

	@Column(name = "grade", columnDefinition = "varchar(20)", nullable = false)
	private String grade;

	@CreatedDate
	@Column(name = "create_at", columnDefinition = "datetime", nullable = false, updatable = false)
	private LocalDateTime createAt;

	@LastModifiedDate
	@Column(name = "update_at", columnDefinition = "datetime", nullable = false)
	private LocalDateTime updateAt;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserEntity that = (UserEntity)o;
		return Objects.equals(userId, that.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(userId);
	}
}
