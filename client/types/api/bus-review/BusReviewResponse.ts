import { BusReviewFactory } from "@/types/api/bus-review/BusReviewFactory"





export default interface BusReviewResponse {
  id: number
  userId: number
  userName: string
  routeId: number
  routeName: string
  busRouteStationId: number
  stationName: string
  content: string
  timeSlot: string
  rating: number
}

export const mockBusReviews: BusReviewResponse[] = [
  BusReviewFactory.createBusReviewResponse(
    1,
    1,
    "userName",
    1,
    "routeName",
    1,
    "사당역(9번출구)",
    "널널해요 ㅎㅎ",
    "T_14_00",
    9
  ),
  BusReviewFactory.createBusReviewResponse(
    2,
    2,
    "userName",
    1,
    "routeName",
    1,
    "사당역(중)",
    "사람이 너무 많아요..",
    "T_17_30",
    5
  ),
  BusReviewFactory.createBusReviewResponse(
    3,
    3,
    "userName",
    1,
    "routeName",
    1,
    "성균관대역, 고용노동청",
    "아침마다 죽겠어요",
    "T_08_00",
    7
  ),
]
