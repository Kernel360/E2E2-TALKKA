package com.talkka.server.bookmark.dao;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.talkka.server.bookmark.enums.TransportType;
import com.talkka.server.subway.enums.Updown;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

	@Column(name = "type", nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	private TransportType type;

	@ManyToOne
	@JoinColumn(name = "bookmark_id")
	private BookmarkEntity bookmark;

	@JoinColumn(name = "subway_station_id")
	private Long subwayStationId;

	@Column(name = "subway_updown")
	private Updown subwayUpdown;

	@JoinColumn(name = "bus_route_station_id")
	private Long busRouteStationId;

	@Column(name = "created_at", nullable = false)
	@CreatedDate
	private LocalDateTime createdAt;

}
