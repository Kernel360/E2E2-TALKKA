package com.talkka.server.bus.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.talkka.server.bus.enums.BusRouteType;
import com.talkka.server.bus.enums.DistrictCode;
import com.talkka.server.bus.util.BusRouteTypeConverter;
import com.talkka.server.bus.util.DistrictCodeConverter;
import com.talkka.server.review.dao.BusReviewEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "bus_route")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BusRouteEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "route_id")
	private Long id;

	@Column(name = "api_route_id", nullable = false, length = 40)
	private String apiRouteId;       // 공공 api 에서 제공해주는 식별자

	@Column(name = "route_name", nullable = false, length = 50)
	private String routeName;       // 노선 번호

	@Column(name = "route_type_cd", nullable = false, length = 3)
	@Convert(converter = BusRouteTypeConverter.class)
	private BusRouteType routeTypeCd;        // 노선 유형 코드

	@Column(name = "route_type_name", nullable = false, length = 50)
	private String routeTypeName;   // 노선 유형명

	@Column(name = "company_id", nullable = false, length = 20)
	private String companyId;       // 운수업체 아이디

	@Column(name = "company_name", nullable = false, length = 50)
	private String companyName;     // 운수업체명

	@Column(name = "company_tel", length = 15)
	private String companyTel;      // 운수업체 전화번호

	@Column(name = "district_cd", nullable = false, length = 3)
	@Convert(converter = DistrictCodeConverter.class)
	private DistrictCode districtCd;         // 관할 지역 코드

	@Column(name = "up_first_time", nullable = false, length = 5)
	private String upFirstTime;     // 평일 기점 첫차 시간

	@Column(name = "up_last_time", nullable = false, length = 5)
	private String upLastTime;      // 평일 기점 막차 시간

	@Column(name = "down_first_time", nullable = false, length = 5)
	private String downFirstTime;   // 평일 종점 첫차 시간

	@Column(name = "down_last_time", nullable = false, length = 5)
	private String downLastTime;    // 평일 종점 막차 시간

	@Column(name = "start_mobile_no", length = 10)
	private String startMobileNo;   // 기점 정류소 번호

	@Column(name = "start_station_id", nullable = false)
	private Long startStationId;    // 기점 정류소 아이디

	@Column(name = "start_station_name", nullable = false, length = 100)
	private String startStationName; // 기점 정류소명

	@Column(name = "end_station_id", nullable = false)
	private Long endStationId;      // 종점 정류소 아이디

	@Column(name = "end_mobile_no", length = 10)
	private String endMobileNo;   // 종점 정류소 번호

	@Column(name = "end_station_name", nullable = false, length = 100)
	private String endStationName;  // 종점 정류소명

	@Column(name = "region_name", length = 100)
	private String regionName;      // 지역명

	@Column(name = "peek_alloc", nullable = false)
	private Integer peekAlloc;          // 평일 최소 배차 시간

	@Column(name = "n_peek_alloc", nullable = false)
	private Integer nPeekAlloc;         // 평일 최대 배차 시간

	@Column(name = "created_at", nullable = false)
	@CreatedDate
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	@LastModifiedDate
	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "route")
	@Builder.Default
	private List<BusRouteStationEntity> stations = new ArrayList<>();

	@OneToMany(mappedBy = "route")
	@Builder.Default
	private List<BusReviewEntity> reviews = new ArrayList<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		BusRouteEntity that = (BusRouteEntity)o;
		return getId().equals(that.getId());
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}
}
