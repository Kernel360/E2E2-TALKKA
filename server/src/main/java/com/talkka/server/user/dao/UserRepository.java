package com.talkka.server.user.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	boolean existsByNickname(String nickname);

	Optional<UserEntity> findByEmail(String email);
}
