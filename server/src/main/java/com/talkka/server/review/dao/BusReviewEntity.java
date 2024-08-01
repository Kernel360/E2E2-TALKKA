package com.talkka.server.review.dao;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.talkka.server.bus.dao.BusRouteEntity;
import com.talkka.server.bus.dao.BusRouteStationEntity;
import com.talkka.server.user.dao.UserEntity;

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
import lombok.Setter;
import lombok.ToString;

@Entity(name = "bus_review")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
public class BusReviewEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bus_review_id", nullable = false)
	private Long busReviewId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity writer;

	@ManyToOne
	@JoinColumn(name = "bus_route_station_id")
	private BusRouteStationEntity station;

	@ManyToOne
	@JoinColumn(name = "route_id")
	private BusRouteEntity route;

	@Column(name = "content")
	private String content;

	@Column(name = "time_slot", nullable = false)
	private Integer timeSlot;

	@Column(name = "rating", nullable = false)
	private Integer rating;

	@CreatedDate
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		BusReviewEntity that = (BusReviewEntity)o;
		return Objects.equals(busReviewId, that.busReviewId);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(busReviewId);
	}
}
