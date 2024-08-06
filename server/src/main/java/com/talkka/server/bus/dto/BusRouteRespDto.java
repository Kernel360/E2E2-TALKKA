package com.talkka.server.bus.dto;

import com.talkka.server.bus.dao.BusRouteEntity;
import com.talkka.server.bus.enums.BusRouteType;
import com.talkka.server.bus.enums.DistrictCode;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BusRouteRespDto {

	private Long routeId;
	private String routeName;       // 노선 번호
	private BusRouteType routeTypeCd;        // 노선 유형 코드
	private String routeTypeName;   // 노선 유형명
	private DistrictCode districtCd;         // 관할 지역 코드
	private String upFirstTime;     // 평일 기점 첫차 시간
	private String upLastTime;      // 평일 기점 막차 시간
	private String downFirstTime;   // 평일 종점 첫차 시간
	private String downLastTime;    // 평일 종점 막차 시간
	private String startMobileNo;   // 기점 정류소 번호
	private Long startStationId;    // 기점 정류소 아이디
	private String startStationName; // 기점 정류소명
	private Long endStationId;      // 종점 정류소 아이디
	private String endMobileNo;   // 종점 정류소 번호
	private String endStationName;  // 종점 정류소명
	private String regionName;      // 지역명
	private Integer peekAlloc;          // 평일 최소 배차 시간
	private Integer nPeekAlloc;         // 평일 최대 배차 시간

	public static BusRouteRespDto of(BusRouteEntity busRouteEntity) {
		return BusRouteRespDto.builder()
			.routeId(busRouteEntity.getId())
			.routeName(busRouteEntity.getRouteName())
			.routeTypeCd(busRouteEntity.getRouteTypeCd())
			.routeTypeName(busRouteEntity.getRouteTypeName())
			.districtCd(busRouteEntity.getDistrictCd())
			.upFirstTime(busRouteEntity.getUpFirstTime())
			.upLastTime(busRouteEntity.getUpLastTime())
			.downFirstTime(busRouteEntity.getDownFirstTime())
			.downLastTime(busRouteEntity.getDownLastTime())
			.startMobileNo(busRouteEntity.getStartMobileNo())
			.startStationId(busRouteEntity.getStartStationId())
			.startStationName(busRouteEntity.getStartStationName())
			.endStationId(busRouteEntity.getEndStationId())
			.endMobileNo(busRouteEntity.getEndMobileNo())
			.endStationName(busRouteEntity.getEndStationName())
			.regionName(busRouteEntity.getRegionName())
			.peekAlloc(busRouteEntity.getPeekAlloc())
			.nPeekAlloc(busRouteEntity.getNPeekAlloc())
			.build();
	}

}
