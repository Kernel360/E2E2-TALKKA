package com.talkka.server.bus.dao;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.talkka.server.bus.enums.PlateType;
import com.talkka.server.bus.util.PlateTypeConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "bus_stat")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BusStatEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "api_route_id", nullable = false)
	private String apiRouteId;

	@Column(name = "api_station_id", nullable = false)
	private String apiStationId;

	@Column(name = "before_seat", nullable = false)
	private Integer beforeSeat;

	@Column(name = "after_seat", nullable = false)
	private Integer afterSeat;

	@Column(name = "seat_diff", nullable = false)
	private Integer seatDiff;

	@Column(name = "before_time", nullable = false)
	private LocalDateTime beforeTime;

	@Column(name = "after_time", nullable = false)
	private LocalDateTime afterTime;

	@Column(name = "plate_no", nullable = false, length = 32)
	private String plateNo;

	@Column(name = "plate_type", nullable = false, length = 1)
	@Convert(converter = PlateTypeConverter.class)
	private PlateType plateType;

	@Column(name = "day_of_week", nullable = false)
	private Integer dayOfWeek;

	@Column(name = "hour", nullable = false)
	private Integer hour;

	@Column(name = "minute", nullable = false)
	private Integer minute;

	@Column(name = "created_at", nullable = false)
	@CreatedDate
	private LocalDateTime createdAt;

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		BusStatEntity that = (BusStatEntity)obj;
		return getId().equals(that.getId());
	}

	// 내용이 중복된 BusStatEntity 식별을 위한 메소드
	public int identifier() {
		return Objects.hash(
			this.getApiRouteId(),
			this.getApiStationId(),
			this.getBeforeSeat(),
			this.getBeforeTime(),
			this.getAfterSeat(),
			this.getAfterTime(),
			this.getSeatDiff(),
			this.getPlateNo(),
			this.getPlateType()
		);
	}
}
