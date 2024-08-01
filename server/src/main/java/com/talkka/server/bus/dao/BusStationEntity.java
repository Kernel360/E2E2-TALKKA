package com.talkka.server.bus.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.talkka.server.bus.enums.CenterStation;
import com.talkka.server.bus.enums.DistrictCode;
import com.talkka.server.bus.enums.TurnStation;
import com.talkka.server.bus.util.CenterStationConverter;
import com.talkka.server.bus.util.DistrictCodeConverter;
import com.talkka.server.bus.util.TurnStationConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "bus_station")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BusStationEntity {

	@Id
	private Long stationId;

	@Column(name = "region_name", nullable = false, length = 100)
	private String regionName;

	@Column(name = "district_cd", nullable = false)
	@Convert(converter = DistrictCodeConverter.class)
	private DistrictCode districtCd;

	@Column(name = "center_yn", nullable = false, length = 1)
	@Convert(converter = CenterStationConverter.class)
	private CenterStation centerYn;

	@Column(name = "turn_yn", nullable = false, length = 1)
	@Convert(converter = TurnStationConverter.class)
	private TurnStation turnYn;

	@Column(name = "longitude", nullable = false, precision = 10, scale = 7)
	private BigDecimal longitude;

	@Column(name = "latitude", nullable = false, precision = 10, scale = 7)
	private BigDecimal latitude;

	@Column(name = "created_at", nullable = false, updatable = false)
	@CreatedDate
	private LocalDateTime createdAt;
}
