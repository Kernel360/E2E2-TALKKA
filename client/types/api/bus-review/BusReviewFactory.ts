import { BusReviewRequest } from "@/types/api/bus-review/BusReviewRequest"
import BusReviewResponse from "@/types/api/bus-review/BusReviewResponse"





export class BusReviewFactory {
  public static createBusReviewResponse(
    id: number,
    userId: number,
    userName: string,
    routeId: number,
    routeName: string,
    busRouteStationId: number,
    stationName: string,
    content: string,
    timeSlot: string,
    rating: number
  ): BusReviewResponse {
    return {
      id,
      userId,
      userName,
      routeId,
      routeName,
      busRouteStationId,
      stationName,
      content,
      timeSlot,
      rating,
    }
  }

  public static createBusReviewRequest(
    routeId: number,
    busRouteStationId: number,
    content: string,
    timeSlot: string,
    rating: number
  ): BusReviewRequest {
    return {
      routeId,
      busRouteStationId,
      content,
      timeSlot,
      rating,
    }
  }
}
