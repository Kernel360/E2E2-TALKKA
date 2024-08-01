package com.talkka.server.bus.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.talkka.server.review.dao.BusReviewEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "bus_route_station")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BusRouteStationEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bus_route_station_id", nullable = false)
	private Long busRouteStationId;

	@ManyToOne
	@JoinColumn(name = "route_id")
	private BusRouteEntity route;

	@ManyToOne
	@JoinColumn(name = "station_id")
	private BusStationEntity station;

	@Column(name = "station_seq", nullable = false)
	private Short stationSeq;

	@Column(name = "station_name", nullable = false, length = 100)
	private String stationName;

	@Column(name = "created_at", nullable = false)
	@CreatedDate
	private LocalDateTime createdAt;

	@OneToMany(mappedBy = "station")
	private List<BusReviewEntity> reviews;
}
