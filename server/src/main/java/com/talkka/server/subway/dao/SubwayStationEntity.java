package com.talkka.server.subway.dao;

import java.util.Objects;

import com.talkka.server.subway.enums.Line;
import com.talkka.server.subway.util.LineConverter;

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

	@Column(name = "station_name", nullable = false, length = 50)
	private String stationName;

	@Column(name = "station_code", nullable = false, length = 10)
	private String stationCode;

	@Column(name = "line_code", nullable = false, length = 4)
	@Convert(converter = LineConverter.class)
	private Line line;

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
