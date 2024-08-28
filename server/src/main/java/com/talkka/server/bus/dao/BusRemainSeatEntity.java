package com.talkka.server.bus.dao;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.talkka.server.bus.enums.PlateType;
import com.talkka.server.bus.util.PlateTypeConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "bus_remain_seat")
@Getter
@Builder(builderMethodName = "defaultBuilder")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BusRemainSeatEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "route_id", nullable = false)
	private BusRouteEntity route;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "station_id", nullable = false)
	private BusStationEntity station;

	@Column(name = "station_seq", nullable = false)
	private Integer stationSeq;

	@Column(name = "empty_seat", nullable = false)
	private Integer emptySeat;

	@Column(name = "plate_no", nullable = false, length = 32)
	private String plateNo;

	@Column(name = "plate_type", nullable = false, length = 1)
	@Convert(converter = PlateTypeConverter.class)
	private PlateType plateType;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "epoch_day", nullable = false)
	private Long epochDay;

	@Column(name = "time", nullable = false)
	private Integer time;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "route_info_id")
	private BusPlateStatisticEntity routeInfo;

	// Builder Class
	public static class BusRemainSeatEntityBuilder {
		public BusRemainSeatEntityBuilder setCreateTime(LocalDateTime createdAt) {
			this.createdAt = createdAt;
			this.epochDay = getEpochDay(createdAt);
			this.time = getTime(createdAt);
			return this;
		}
	}

	public static BusRemainSeatEntityBuilder build() {
		return defaultBuilder();
	}

	public void updateRouteInfo(BusPlateStatisticEntity routeInfo) {
		this.routeInfo = routeInfo;
	}

	/**
	 * 주어진 {@code LocalDateTime} 객체의 시간과 분을 이어붙여 정수 형태로 반환합니다.
	 * 예를 들어, 23:59는 2359로, 08:27은 827로 반환됩니다.
	 *
	 * @param localDateTime 시간을 추출할 {@code LocalDateTime} 객체입니다.
	 * @return 시간과 분을 이어붙인 정수 값입니다.
	 */
	private static int getTime(LocalDateTime localDateTime) {
		return localDateTime.getHour() * 100 + localDateTime.getMinute();
	}

	/**
	 * 주어진 {@code LocalDateTime} 객체에 대해 새벽 3시를 기준으로 날짜를 변경하여 epochDay 값을 반환합니다.
	 * 시간대가 3시 이전인 경우, 전날의 epochDay 를 반환하며, 그렇지 않은 경우 해당 날짜의 epochDay 를 반환합니다.
	 * 반환되는 값은 1에서 7 사이의 값을 가집니다.
	 *
	 * @param localDateTime 날짜를 계산할 {@code LocalDateTime} 객체입니다.
	 * @return 새벽 3시를 기준으로 계산된 epochDay 값입니다.
	 */
	private static Long getEpochDay(LocalDateTime localDateTime) {
		if (localDateTime.getHour() < 3) {
			return localDateTime.toLocalDate().toEpochDay() - 1;
		}
		return localDateTime.toLocalDate().toEpochDay();
	}

}
