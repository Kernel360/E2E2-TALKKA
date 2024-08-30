package com.talkka.server.bookmark.dao;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.talkka.server.bus.dao.BusRouteStationEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "bookmark_detail")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BookmarkDetailEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bookmark_detail_id", nullable = false)
	private Long id;

	@Column(name = "seq", nullable = false)
	private Integer seq;

	@ManyToOne
	@JoinColumn(name = "bookmark_id")
	private BookmarkEntity bookmark;

	@ManyToOne
	@JoinColumn(name = "bus_route_station_id")
	private BusRouteStationEntity routeStation;

	@Column(name = "created_at", nullable = false)
	@CreatedDate
	private LocalDateTime createdAt;
}
