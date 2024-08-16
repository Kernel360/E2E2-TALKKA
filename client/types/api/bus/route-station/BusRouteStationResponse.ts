import { BusRouteStationFactory } from "@/types/api/bus/route-station/BusRouteStationFactory"





export default interface BusRouteStationResponse {
  busRouteStationId: number // 버스 노선 정류소 아이디
  stationSeq: number // 정류소 순번
  stationName: string // 정류소 이름
  routeId: number // 노선 아이디
  routeName: string // 노선 이름
  stationId: number // 정류소 아이디
  createdAt: string // 생성 일자 (문자열 형식)
}

export const mockBusRouteStations: BusRouteStationResponse[] = [
  BusRouteStationFactory.createBusRouteStationResponse(
    1,
    1,
    "사당역(9번출구)",
    1,
    "routeName",
    1,
    "2021-01-01"
  ),
  BusRouteStationFactory.createBusRouteStationResponse(
    2,
    2,
    "사당역(중)",
    1,
    "routeName",
    2,
    "2021-01-01"
  ),
  BusRouteStationFactory.createBusRouteStationResponse(
    3,
    3,
    "성균관대역, 고용노동청",
    1,
    "routeName",
    3,
    "2021-01-01"
  ),
]
