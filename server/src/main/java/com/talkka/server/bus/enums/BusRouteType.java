package com.talkka.server.bus.enums;

import lombok.Getter;

/**
 * 노선유형 코드	노선유형명
 * 11	직행좌석형시내버스	23	일반형농어촌버스
 * 12	좌석형시내버스	30	마을버스
 * 13	일반형시내버스	41	고속형시외버스
 * 14	광역급행형시내버스	42	좌석형시외버스
 * 15	따복형 시내버스	43	일반형시외버스
 * 16	경기순환버스	51	리무진공항버스
 * 21	직행좌석형농어촌버스	52	좌석형공항버스
 * 22	좌석형농어촌버스	53	일반형공항버스
 */
@Getter
public enum BusRouteType {
	DIRECT_SEAT_CITY_BUS(11, "직행좌석형시내버스"),
	SEAT_CITY_BUS(12, "좌석형시내버스"),
	GENERAL_CITY_BUS(13, "일반형시내버스"),
	EXPRESS_CITY_BUS(14, "광역급행형시내버스"),
	DDABOK_CITY_BUS(15, "따복형 시내버스"),
	GYEONGGI_CIRCULATION_BUS(16, "경기순환버스"),
	DIRECT_SEAT_RURAL_BUS(21, "직행좌석형농어촌버스"),
	SEAT_RURAL_BUS(22, "좌석형농어촌버스"),
	GENERAL_RURAL_BUS(23, "일반형농어촌버스"),
	VILLAGE_BUS(30, "마을버스"),
	HIGH_SPEED_INTERCITY_BUS(41, "고속형시외버스"),
	SEAT_INTERCITY_BUS(42, "좌석형시외버스"),
	GENERAL_INTERCITY_BUS(43, "일반형시외버스"),
	LIMOUSINE_AIRPORT_BUS(51, "리무진공항버스"),
	SEAT_AIRPORT_BUS(52, "좌석형공항버스"),
	GENERAL_AIRPORT_BUS(53, "일반형공항버스");

	private final int code;
	private final String name;

	BusRouteType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public static BusRouteType fromInt(int value) {
		return switch (value) {
			case 11 -> DIRECT_SEAT_CITY_BUS;
			case 12 -> SEAT_CITY_BUS;
			case 13 -> GENERAL_CITY_BUS;
			case 14 -> EXPRESS_CITY_BUS;
			case 15 -> DDABOK_CITY_BUS;
			case 16 -> GYEONGGI_CIRCULATION_BUS;
			case 21 -> DIRECT_SEAT_RURAL_BUS;
			case 22 -> SEAT_RURAL_BUS;
			case 23 -> GENERAL_RURAL_BUS;
			case 30 -> VILLAGE_BUS;
			case 41 -> HIGH_SPEED_INTERCITY_BUS;
			case 42 -> SEAT_INTERCITY_BUS;
			case 43 -> GENERAL_INTERCITY_BUS;
			case 51 -> LIMOUSINE_AIRPORT_BUS;
			case 52 -> SEAT_AIRPORT_BUS;
			case 53 -> GENERAL_AIRPORT_BUS;
			default -> throw new IllegalArgumentException("Invalid value: " + value); // DOMAIN EXCEPTION 으로 변환 필요
		};
	}
}
