package com.talkka.server.bus.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.data.annotation.CreatedDate;

import com.talkka.server.bus.enums.CenterStation;
import com.talkka.server.bus.enums.DistrictCode;
import com.talkka.server.bus.enums.TurnStation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "bus_route_station")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusRouteStationEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long busRouteStationId;

	@Column(name = "route_id", nullable = false, columnDefinition = "BIGINT")
	private Long routeId;

	@Column(name = "station_id", nullable = false, columnDefinition = "BIGINT")
	private Long stationId;

	@Column(name = "station_seq", nullable = false, columnDefinition = "SMALLINT")
	private Short stationSeq;

	@Column(name = "station_name", nullable = false, length = 100)
	private String stationName;

	@Column(name = "region_name", nullable = false, length = 100)
	private String regionName;

	@Column(name = "district_cd", nullable = false)
	@Enumerated(EnumType.STRING)
	private DistrictCode districtCd;

	@Column(name = "center_yn", nullable = false)
	@Enumerated(EnumType.STRING)
	private CenterStation centerYn;

	@Column(name = "turn_yn", nullable = false)
	private TurnStation turnYn;

	@Column(name = "longitude", nullable = false, columnDefinition = "DECIMAL", precision = 10, scale = 7)
	private BigDecimal longitude;

	@Column(name = "latitude", nullable = false, columnDefinition = "DECIMAL", precision = 10, scale = 7)
	private BigDecimal latitude;

	@Column(name = "created_at", nullable = false)
	@CreatedDate
	private Timestamp createdAt;
}
