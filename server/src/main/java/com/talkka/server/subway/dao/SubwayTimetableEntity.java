package com.talkka.server.subway.dao;

import java.time.LocalTime;
import java.util.Objects;

import com.talkka.server.subway.enums.DayType;
import com.talkka.server.subway.enums.Express;
import com.talkka.server.subway.enums.Line;
import com.talkka.server.subway.enums.Updown;
import com.talkka.server.subway.util.DayTypeConverter;
import com.talkka.server.subway.util.ExpressConverter;
import com.talkka.server.subway.util.LineConverter;
import com.talkka.server.subway.util.UpdownConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "subway_timetable")
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubwayTimetableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subway_timetable_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "station_id", nullable = false)
	private SubwayStationEntity subwayStation;

	@Column(name = "station_code", nullable = false, length = 10)
	private String stationCode;

	@Column(name = "station_name", nullable = false, length = 50)
	private String stationName;

	@Column(name = "line_code", nullable = false, length = 4)
	@Convert(converter = LineConverter.class)
	private Line line;
	
	@Column(name = "day_type", nullable = false, length = 3)
	@Convert(converter = DayTypeConverter.class)
	private DayType dayType;

	@Column(name = "updown", nullable = false, length = 1)
	@Convert(converter = UpdownConverter.class)
	private Updown updown;

	@Column(name = "is_express", nullable = false, length = 1)
	@Convert(converter = ExpressConverter.class)
	private Express express;

	@Column(name = "train_code", nullable = false, length = 10)
	private String trainCode;

	@Column(name = "arrival_time", nullable = false)
	private LocalTime arrivalTime;

	@Column(name = "depart_time", nullable = false)
	private LocalTime departTime;

	@Column(name = "start_station_name", nullable = false, length = 50)
	private String startStationName;

	@Column(name = "end_station_name", nullable = false, length = 50)
	private String endStationName;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SubwayTimetableEntity that = (SubwayTimetableEntity)o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}
