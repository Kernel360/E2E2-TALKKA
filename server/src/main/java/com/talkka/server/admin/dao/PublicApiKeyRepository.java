package com.talkka.server.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicApiKeyRepository extends JpaRepository<PublicApiKeyEntity, Long> {
	boolean existsBySecret(String secret);

	void deleteBySecret(String secret);
}
