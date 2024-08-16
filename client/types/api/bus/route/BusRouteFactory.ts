import { BusRouteResponse } from "@/types/api/bus/route/BusRouteResponse"





export class BusRouteFactory {
  public static createBusRouteResponse(
    routeId: number,
    routeName: string,
    routeTypeCd: string,
    routeTypeName: string,
    districtCd: string,
    upFirstTime: string,
    upLastTime: string,
    downFirstTime: string,
    downLastTime: string,
    startMobileNo: string,
    startStationId: number,
    startStationName: string,
    endStationId: number,
    endMobileNo: string,
    endStationName: string,
    regionName: string,
    peekAlloc: number,
    nPeekAlloc: number
  ): BusRouteResponse {
    return {
      routeId,
      routeName,
      routeTypeCd,
      routeTypeName,
      districtCd,
      upFirstTime,
      upLastTime,
      downFirstTime,
      downLastTime,
      startMobileNo,
      startStationId,
      startStationName,
      endStationId,
      endMobileNo,
      endStationName,
      regionName,
      peekAlloc,
      nPeekAlloc,
    }
  }
}
