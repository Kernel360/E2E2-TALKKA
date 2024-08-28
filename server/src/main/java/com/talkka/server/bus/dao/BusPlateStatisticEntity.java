package com.talkka.server.bus.dao;

import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.talkka.server.bus.enums.PlateType;

import jakarta.persistence.CascadeType;
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

@Entity(name = "bus_plate_statistic")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BusPlateStatisticEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "route_id", nullable = false)
	private BusRouteEntity route;

	@Column(name = "plate_type", nullable = false)
	private PlateType plateType;

	@Column(name = "plate_no", nullable = false)
	private String plateNo;

	@Column(name = "epoch_day", nullable = false)
	private Long epochDay;

	@Column(name = "start_time", nullable = false)
	private Integer startTime;

	@Column(name = "end_time", nullable = false)
	private Integer endTime;

	@OneToMany(mappedBy = "routeInfo", cascade = CascadeType.PERSIST)
	private List<BusRemainSeatEntity> seats;
}
