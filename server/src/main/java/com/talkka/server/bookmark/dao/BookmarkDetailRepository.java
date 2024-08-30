package com.talkka.server.bookmark.dao;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookmarkDetailRepository extends JpaRepository<BookmarkDetailEntity, Long> {
	@Query("SELECT b.routeStation.id AS key, COUNT(b) AS value FROM bookmark_detail b GROUP BY b.routeStation.id HAVING COUNT(b) > 0")
	Map<Long, Integer> countGroupedByBusRouteStationId();
}
