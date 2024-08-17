package com.talkka.server.bookmark.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Long> {
	List<BookmarkEntity> findByUserId(Long userId);

	boolean existsByIdAndUserId(Long bookmarkId, Long userId);
}
