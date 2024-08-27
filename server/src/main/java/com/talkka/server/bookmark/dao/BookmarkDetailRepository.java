package com.talkka.server.bookmark.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkDetailRepository extends JpaRepository<BookmarkDetailEntity, Long> {
	List<BookmarkDetailEntity> findByBookmarkId(Long bookmarkId);

	void deleteByBookmark(BookmarkEntity bookmark);

	List<BookmarkDetailEntity> deleteByBookmarkId(Long bookmarkId);
}
