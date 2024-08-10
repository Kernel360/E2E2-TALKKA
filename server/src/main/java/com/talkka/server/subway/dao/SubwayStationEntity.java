package com.talkka.server.subway.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.talkka.server.subway.enums.Line;
import com.talkka.server.subway.util.LineConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "subway_station")
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubwayStationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "station_id", nullable = false)
	private Long id;

	@Column(name = "api_station_id")
	private String apiStationId;

	@Column(name = "station_name", nullable = false)
	private String stationName;

	@Column(name = "fr_code", nullable = false)
	private String frCode;

	@Column(name = "line_code", nullable = false)
	@Convert(converter = LineConverter.class)
	private Line line;

	@OneToMany(mappedBy = "subwayStation")
	@Builder.Default
	private List<SubwayTimetableEntity> timetables = new ArrayList<>();

	@OneToMany(mappedBy = "subwayStation")
	@Builder.Default
	private List<SubwayConfusionEntity> confusions = new ArrayList<>();

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SubwayStationEntity that = (SubwayStationEntity)o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}
