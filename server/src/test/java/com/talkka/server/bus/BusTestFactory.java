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

	public static BusRouteEntity getBusRouteEntity(Long id) {
		return BusRouteEntity.builder()
			.id(id)
			.apiRouteId("BRT" + id)
			.routeName("7800" + id)
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

	public static BusRouteCreateDto getBusRouteCreateDto(Long id) {

		return BusRouteCreateDto.builder()
			.apiRouteId("BRT" + id)
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

	public static BusRouteRespDto getBusRouteRespDto(Long id) {
		return BusRouteRespDto.builder()
			.routeId(id)
			.routeName("7800" + id)
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

	public static BusStationEntity getBusStationEntity(Long id) {
		return BusStationEntity.builder()
			.id(id)
			.apiStationId("BST" + id)
			.stationName("정거장" + id)
			.regionName("서울")
			.districtCd(DistrictCode.DONGDUCHEON)
			.centerYn(CenterStation.CENTER_STATION)
			.turnYn(TurnStation.TURN_STATION)
			.longitude(BigDecimal.valueOf(127.123456))
			.latitude(BigDecimal.valueOf(37.123456))
			.build();
	}

	public static BusStationCreateDto getBusStationCreateDto(Long id) {
		return BusStationCreateDto.builder()
			.apiStationId("BST" + id)
			.stationName("정거장" + id)
			.regionName("서울")
			.districtCd(DistrictCode.DONGDUCHEON)
			.centerYn(CenterStation.CENTER_STATION)
			.turnYn(TurnStation.TURN_STATION)
			.longitude(BigDecimal.valueOf(127.123456))
			.latitude(BigDecimal.valueOf(37.123456))
			.build();
	}

	protected static BusStationRespDto getBusStationRespDto(Long id) {
		return BusStationRespDto.builder()
			.stationId(id)
			.stationName("정거장" + id)
			.regionName("서울")
			.districtCd(DistrictCode.DONGDUCHEON)
			.centerYn(CenterStation.CENTER_STATION)
			.turnYn(TurnStation.TURN_STATION)
			.longitude(BigDecimal.valueOf(127.123456))
			.latitude(BigDecimal.valueOf(37.123456))
			.build();
	}

	protected static BusRouteStationEntity getBusRouteStationEntity(Long id, BusRouteEntity routeEntity,
		BusStationEntity stationEntity) {
		return BusRouteStationEntity.builder()
			.id(id)
			.route(routeEntity)
			.station(stationEntity)
			.stationName("정거장" + id)
			.stationSeq(Short.valueOf(String.valueOf(id)))
			.build();
	}

	protected static BusRouteStationCreateDto getBusRouteStationCreateDto(Long id) {
		return BusRouteStationCreateDto.builder()
			.apiRouteId("BRT" + id)
			.apiStationId("BST" + id)
			.stationName("정거장" + id)
			.stationSeq(Short.valueOf(String.valueOf(id)))
			.build();
	}

	protected static BusRouteStationRespDto getBusRouteStationRespDto(Long id, BusRouteRespDto routeRespDto,
		BusStationRespDto stationRespDto) {
		return BusRouteStationRespDto.builder()
			.busRouteStationId(id)
			.route(routeRespDto)
			.station(stationRespDto)
			.stationName("정거장" + id)
			.stationSeq(Short.valueOf(String.valueOf(id)))
			.build();
	}

}
