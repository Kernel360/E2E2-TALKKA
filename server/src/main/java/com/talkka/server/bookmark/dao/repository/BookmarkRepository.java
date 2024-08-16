package com.talkka.server.bookmark.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talkka.server.bookmark.dao.entity.BookmarkEntity;

public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Long> {
	List<BookmarkEntity> findByUserId(Long userId);

	boolean existsByIdAndUserId(Long bookmarkId, Long userId);
}
