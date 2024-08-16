export interface BusReviewRequest {
  routeId: number
  busRouteStationId: number
  content: string
  timeSlot: string
  rating: number
}
