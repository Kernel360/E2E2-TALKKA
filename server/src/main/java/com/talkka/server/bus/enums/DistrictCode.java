package com.talkka.server.bus.enums;

import lombok.Getter;

/**
 * 운행지역 ID	운행지역명
 * 01	가평군	12	성남시	23	용인시
 * 02	고양시	13	수원시	24	의왕시
 * 03	과천시	14	시흥시	25	의정부시
 * 04	광명시	15	안산시	26	이천시
 * 05	광주시	16	안성시	27	파주시
 * 06	구리시	17	안양시	28	평택시
 * 07	군포시	18	양주시	29	포천시
 * 08	김포시	19	양평군	30	하남시
 * 09	남양주시	20	여주군	31	화성시
 * 10	동두천시	21	연천군	32	서울특별시
 * 11	부천시	22	오산시	33	인천광역시
 */
@Getter
public enum DistrictCode {
	GAPAENG_GUN("1", "가평군"),
	GOYANG("2", "고양시"),
	GWACHEON("3", "과천시"),
	GWANGMYEONG("4", "광명시"),
	GWANGJU("5", "광주시"),
	GURI("6", "구리시"),
	GUNPO("7", "군포시"),
	KIMPO("8", "김포시"),
	NAMYANGJU("9", "남양주시"),
	DONGDUCHEON("10", "동두천시"),
	BUCHEON("11", "부천시"),
	SEONGNAM("12", "성남시"),
	SUWON("13", "수원시"),
	SIHEUNG("14", "시흥시"),
	ANSAN("15", "안산시"),
	ANSEONG("16", "안성시"),
	ANYANG("17", "안양시"),
	YANGJU("18", "양주시"),
	YANGPYEONG("19", "양평군"),
	YEJU("20", "여주군"),
	YEONCHEON("21", "연천군"),
	OSAN("22", "오산시"),
	YONGIN("23", "용인시"),
	UIWANG("24", "의왕시"),
	UJEONGBU("25", "의정부시"),
	ICHUN("26", "이천시"),
	PAJU("27", "파주시"),
	PYEONGTAEK("28", "평택시"),
	POCHON("29", "포천시"),
	HWASEONG("30", "화성시"),
	HANNAM("31", "하남시"),
	SEOUL("32", "서울특별시"),
	INCHEON("33", "인천광역시");

	private final String code;
	private final String name;

	DistrictCode(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public static DistrictCode fromCode(String value) {
		return switch (value) {
			case "1" -> GAPAENG_GUN;
			case "2" -> GOYANG;
			case "3" -> GWACHEON;
			case "4" -> GWANGMYEONG;
			case "5" -> GWANGJU;
			case "6" -> GURI;
			case "7" -> GUNPO;
			case "8" -> KIMPO;
			case "9" -> NAMYANGJU;
			case "10" -> DONGDUCHEON;
			case "11" -> BUCHEON;
			case "12" -> SEONGNAM;
			case "13" -> SUWON;
			case "14" -> SIHEUNG;
			case "15" -> ANSAN;
			case "16" -> ANSEONG;
			case "17" -> ANYANG;
			case "18" -> YANGJU;
			case "19" -> YANGPYEONG;
			case "20" -> YEJU;
			case "21" -> YEONCHEON;
			case "22" -> OSAN;
			case "23" -> YONGIN;
			case "24" -> UIWANG;
			case "25" -> UJEONGBU;
			case "26" -> ICHUN;
			case "27" -> PAJU;
			case "28" -> PYEONGTAEK;
			case "29" -> POCHON;
			case "30" -> HWASEONG;
			case "31" -> HANNAM;
			case "32" -> SEOUL;
			case "33" -> INCHEON;
			default -> throw new IllegalArgumentException("Invalid value: " + value); // DOMAIN EXCEPTION 으로 변환 필요
		};
	}
}
