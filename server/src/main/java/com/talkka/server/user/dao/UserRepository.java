package com.talkka.server.user.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talkka.server.user.vo.Email;
import com.talkka.server.user.vo.Nickname;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	boolean existsByNickname(Nickname nickname);

	Optional<UserEntity> findByEmail(Email email);
}
