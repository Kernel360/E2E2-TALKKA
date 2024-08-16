package com.talkka.server.bookmark.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talkka.server.bookmark.dao.entity.BookmarkDetailEntity;

public interface BookmarkDetailRepository extends JpaRepository<BookmarkDetailEntity, Long> {
	List<BookmarkDetailEntity> findByBookmarkId(Long bookmarkId);

	List<BookmarkDetailEntity> deleteByBookmarkId(Long bookmarkId);
}
