import { redirect } from "next/navigation"
import serverFetch, { FetchResult } from "@/utils/serverFetch"

import BusReviewResponse from "@/types/api/bus-review/BusReviewResponse"
import { BusRouteResponse } from "@/types/api/bus/route/BusRouteResponse"
import BaseReviewContainer from "@/app/review/components/BaseReviewContainer"
import BusCard from "@/app/review/components/BusInfoCard"
import BusReviewListContainer from "@/app/review/components/BusReviewListContainer"
import RouteStationSelect from "@/app/review/components/RouteStationSelect"
import SearchBusRoute from "@/app/review/components/SearchBusRoute"





const getQuery = (routeId?: string, stationId?: string, timeSlot?: string) => {
  if (!routeId) return new URLSearchParams()
  const query = new URLSearchParams()
  query.append("routeId", routeId.toString())
  if (stationId) query.append("stationId", stationId.toString())
  if (timeSlot) query.append("timeSlot", timeSlot)
  return query
}

const createUrl = (path: string, query: URLSearchParams) => {
  return `${path}?${query.toString()}`
}

interface SearchParams {
  routeId?: string
  stationId?: string
  timeSlot?: string
}

export default async function BusReviewPage({
  params,
  searchParams,
}: {
  params: { slug: string }
  searchParams: SearchParams
}) {
  let routeId = searchParams?.routeId ?? undefined
  let stationId = searchParams?.stationId ?? undefined
  let timeSlot = searchParams.timeSlot ?? undefined
  let reviews: BusReviewResponse[] = []
  let busRoute: BusRouteResponse | null = null
  if (routeId) {
    // review list
    const query = getQuery(routeId, stationId, timeSlot)
    const apiUrl = createUrl("/api/bus-review", query)
    const resp: FetchResult<BusReviewResponse[]> = await serverFetch<
      BusReviewResponse[]
    >(apiUrl, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    })
    if (resp.data) {
      reviews = resp.data
    }

    const routeUrl = `/api/bus/route/${routeId}`
    const routeResp: FetchResult<BusRouteResponse> =
      await serverFetch<BusRouteResponse>(routeUrl, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      })
    if (routeResp.error) {
      redirect("/review/bus")
    }
    if (routeResp.data) {
      busRoute = routeResp.data
    }
  }
  return (
    <BaseReviewContainer defaultTransport={"BUS"}>
      <SearchBusRoute />
      {routeId && <RouteStationSelect routeId={routeId} />}
      <BusCard busRoute={busRoute}></BusCard>
      <p className={`font-extrabold`}>리뷰 모아보기</p>
      <BusReviewListContainer reviews={reviews} />
    </BaseReviewContainer>
  )
}
