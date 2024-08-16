import { BusRouteFactory } from "@/types/api/bus/route/BusRouteFactory"





export interface BusRouteResponse {
  routeId: number // 노선 아이디
  routeName: string // 노선 번호
  routeTypeCd: string // 노선 유형 코드
  routeTypeName: string // 노선 유형명
  districtCd: string // 관할 지역 코드
  upFirstTime: string // 평일 기점 첫차 시간
  upLastTime: string // 평일 기점 막차 시간
  downFirstTime: string // 평일 종점 첫차 시간
  downLastTime: string // 평일 종점 막차 시간
  startMobileNo: string // 기점 정류소 번호
  startStationId: number // 기점 정류소 아이디
  startStationName: string // 기점 정류소명
  endStationId: number // 종점 정류소 아이디
  endMobileNo: string // 종점 정류소 번호
  endStationName: string // 종점 정류소명
  regionName: string // 지역명
  peekAlloc: number // 평일 최소 배차 시간
  nPeekAlloc: number // 평일 최대 배차 시간
}

export const mockBusRoutes: BusRouteResponse[] = [
  BusRouteFactory.createBusRouteResponse(
    1,
    "101",
    "BUS",
    "버스",
    "D1",
    "06:00",
    "22:00",
    "05:30",
    "21:30",
    "001",
    1001,
    "서울역",
    2001,
    "002",
    "강남역",
    "서울",
    10,
    30
  ),
  BusRouteFactory.createBusRouteResponse(
    2,
    "202",
    "BUS",
    "버스",
    "D2",
    "06:30",
    "23:00",
    "06:00",
    "22:30",
    "003",
    1002,
    "홍대입구",
    2002,
    "004",
    "잠실역",
    "서울",
    15,
    25
  ),
]
