package com.talkka.server.bus;

import java.math.BigDecimal;

import com.talkka.server.bus.dao.BusRouteEntity;
import com.talkka.server.bus.dao.BusRouteStationEntity;
import com.talkka.server.bus.dao.BusStationEntity;
import com.talkka.server.bus.dto.BusRouteCreateDto;
import com.talkka.server.bus.dto.BusRouteRespDto;
import com.talkka.server.bus.dto.BusRouteStationCreateDto;
import com.talkka.server.bus.dto.BusRouteStationRespDto;
import com.talkka.server.bus.dto.BusStationCreateDto;
import com.talkka.server.bus.dto.BusStationRespDto;
import com.talkka.server.bus.enums.BusRouteType;
import com.talkka.server.bus.enums.CenterStation;
import com.talkka.server.bus.enums.DistrictCode;
import com.talkka.server.bus.enums.TurnStation;

public class BusTestFactory {

	public static BusRouteEntity getBusRouteEntity(Long routeId) {
		return BusRouteEntity.builder()
			.id(routeId)
			.apiRouteId("apiRouteId")
			.routeName("7800")
			.routeTypeCd(BusRouteType.DIRECT_SEAT_CITY_BUS)
			.routeTypeName(BusRouteType.DIRECT_SEAT_CITY_BUS.getName())
			.companyId("COMP123")
			.companyName("수형운수")
			.companyTel("02-123-4567")
			.districtCd(DistrictCode.DONGDUCHEON)
			.upFirstTime("05:30")
			.upLastTime("23:00")
			.downFirstTime("06:00")
			.downLastTime("00:35")
			.startMobileNo("101")
			.startStationId(1001L)
			.startStationName("기점 정류소")
			.endStationId(2002L)
			.endMobileNo("202")
			.endStationName("종점 정류소")
			.regionName("서울")
			.peekAlloc(15)
			.nPeekAlloc(25)
			.build();
	}

	public static BusRouteCreateDto getBusRouteCreateDto(String apiRouteId) {

		return BusRouteCreateDto.builder()
			.apiRouteId("apiRouteId")
			.routeName("7800")
			.routeTypeCd(BusRouteType.DIRECT_SEAT_CITY_BUS)
			.routeTypeName(BusRouteType.DIRECT_SEAT_CITY_BUS.getName())
			.companyId("COMP123")
			.companyName("수형운수")
			.companyTel("02-123-4567")
			.districtCd(DistrictCode.DONGDUCHEON)
			.upFirstTime("05:30")
			.upLastTime("23:00")
			.downFirstTime("06:00")
			.downLastTime("00:35")
			.startMobileNo("101")
			.startStationId(1001L)
			.startStationName("기점 정류소")
			.endStationId(2002L)
			.endMobileNo("202")
			.endStationName("종점 정류소")
			.regionName("서울")
			.peekAlloc(15)
			.nPeekAlloc(25)
			.build();
	}

	public static BusRouteRespDto getBusRouteRespDto(Long routeId) {
		return BusRouteRespDto.builder()
			.routeId(routeId)
			.apiRouteId("apiRouteId")
			.routeName("7800")
			.routeTypeCd(BusRouteType.DIRECT_SEAT_CITY_BUS)
			.routeTypeName(BusRouteType.DIRECT_SEAT_CITY_BUS.getName())
			.districtCd(DistrictCode.DONGDUCHEON)
			.upFirstTime("05:30")
			.upLastTime("23:00")
			.downFirstTime("06:00")
			.downLastTime("00:35")
			.startMobileNo("101")
			.startStationId(1001L)
			.startStationName("기점 정류소")
			.endStationId(2002L)
			.endMobileNo("202")
			.endStationName("종점 정류소")
			.regionName("서울")
			.peekAlloc(15)
			.nPeekAlloc(25)
			.build();
	}

	public static BusStationEntity getBusStationEntity(Long stationId) {
		return BusStationEntity.builder()
			.id(stationId)
			.apiStationId("apiStationId")
			.stationName("7800 정류장")
			.regionName("서울")
			.districtCd(DistrictCode.DONGDUCHEON)
			.centerYn(CenterStation.CENTER_STATION)
			.turnYn(TurnStation.TURN_STATION)
			.longitude(BigDecimal.valueOf(127.123456))
			.latitude(BigDecimal.valueOf(37.123456))
			.build();
	}

	public static BusStationCreateDto getBusStationCreateDto(String apiStationId) {
		return BusStationCreateDto.builder()
			.apiStationId(apiStationId)
			.stationName("7800 정류장")
			.regionName("서울")
			.districtCd(DistrictCode.DONGDUCHEON)
			.centerYn(CenterStation.CENTER_STATION)
			.turnYn(TurnStation.TURN_STATION)
			.longitude(BigDecimal.valueOf(127.123456))
			.latitude(BigDecimal.valueOf(37.123456))
			.build();
	}

	public static BusStationRespDto getBusStationRespDto(Long stationId) {
		return BusStationRespDto.builder()
			.stationId(stationId)
			.apiStationId("apiStationId")
			.stationName("7800 정류장")
			.regionName("서울")
			.districtCd(DistrictCode.DONGDUCHEON)
			.centerYn(CenterStation.CENTER_STATION)
			.turnYn(TurnStation.TURN_STATION)
			.longitude(BigDecimal.valueOf(127.123456))
			.latitude(BigDecimal.valueOf(37.123456))
			.build();
	}

	public static BusRouteStationEntity getBusRouteStationEntity(
		Long routeStationId, BusRouteEntity routeEntity, BusStationEntity stationEntity
	) {
		return BusRouteStationEntity.builder()
			.id(routeStationId)
			.route(routeEntity)
			.station(stationEntity)
			.stationName("7800 정류장")
			.stationSeq(Short.valueOf(String.valueOf(routeStationId)))
			.build();
	}

	public static BusRouteStationCreateDto getBusRouteStationCreateDto(Long routeId, Long stationId) {
		return BusRouteStationCreateDto.builder()
			.routeId(routeId)
			.stationId(stationId)
			.stationName("7800 정류장")
			.stationSeq(Short.valueOf(String.valueOf(routeId)))
			.build();
	}

	public static BusRouteStationRespDto getBusRouteStationRespDto(
		Long routeStationId, BusRouteRespDto routeRespDto, BusStationRespDto stationRespDto) {
		return BusRouteStationRespDto.builder()
			.busRouteStationId(routeStationId)
			.stationId(stationRespDto.stationId())
			.stationName(stationRespDto.stationName())
			.stationSeq(Short.valueOf(String.valueOf(routeStationId)))
			.routeId(routeRespDto.routeId())
			.build();
	}

}
