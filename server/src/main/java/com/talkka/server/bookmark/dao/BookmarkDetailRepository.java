package com.talkka.server.bookmark.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookmarkDetailRepository extends JpaRepository<BookmarkDetailEntity, Long> {
	List<BookmarkDetailEntity> findByBookmarkId(Long bookmarkId);

	List<BookmarkDetailEntity> deleteByBookmarkId(Long bookmarkId);

	@Query("SELECT b.busRouteStationId AS key, COUNT(b) AS value FROM bookmark_detail b GROUP BY b.busRouteStationId HAVING COUNT(b) > 0")
	Map<Long, Integer> countGroupedByBusRouteStationId();

}
