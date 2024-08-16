import BusRouteStationResponse from "@/types/api/bus/route-station/BusRouteStationResponse"

export class BusRouteStationFactory {
  public static createBusRouteStationResponse(
    busRouteStationId: number,
    stationSeq: number,
    stationName: string,
    routeId: number,
    routeName: string,
    stationId: number,
    createdAt: string
  ): BusRouteStationResponse {
    return {
      busRouteStationId,
      stationSeq,
      stationName,
      routeId,
      routeName,
      stationId,
      createdAt,
    }
  }
}
