package com.talkka.server.bus.dao;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import com.talkka.server.bus.enums.EndBus;
import com.talkka.server.bus.enums.LowPlate;
import com.talkka.server.bus.enums.PlateType;
import com.talkka.server.bus.util.EndBusConverter;
import com.talkka.server.bus.util.LowPlateConverter;
import com.talkka.server.bus.util.PlateTypeConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "bus_location")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusLocationEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bus_location_id", nullable = false)
	private Long busLocationId;

	@Column(name = "route_id", nullable = false)
	private Long routeId;

	@Column(name = "station_id", nullable = false)
	private Long stationId;

	@Column(name = "station_seq", nullable = false, columnDefinition = "SMALLINT")
	private Short stationSeq;

	@Column(name = "end_bus", nullable = false, length = 1)
	@Convert(converter = EndBusConverter.class)
	private EndBus endBus;

	@Column(name = "low_plate", nullable = false, length = 1)
	@Convert(converter = LowPlateConverter.class)
	private LowPlate lowPlate;

	@Column(name = "plate_no", nullable = false, length = 32)
	private String plateNo;

	@Column(name = "plate_type", nullable = false, length = 1)
	@Convert(converter = PlateTypeConverter.class)
	private PlateType plateType;

	@Column(name = "remain_seat_count", nullable = false, columnDefinition = "SMALLINT")
	private Short remainSeatCount;

	@Column(name = "created_at", nullable = false)
	@CreatedDate
	private LocalDateTime createdAt;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		BusLocationEntity that = (BusLocationEntity)o;
		return getBusLocationId().equals(that.getBusLocationId());
	}

	@Override
	public int hashCode() {
		return getBusLocationId().hashCode();
	}
}
