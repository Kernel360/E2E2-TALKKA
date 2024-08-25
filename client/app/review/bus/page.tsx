import { redirect } from "next/navigation"
import useClient from "@/api/useClient"
import { components } from "@/api/v1"

import { TimeSlot } from "@/types/api/domain/TimeSlot"
import BaseReviewContainer from "@/app/review/components/BaseReviewContainer"
import BusCard from "@/app/review/components/BusInfoCard"
import BusReviewListContainer from "@/app/review/components/BusReviewListContainer"
import ReviewCreateButton from "@/app/review/components/ReviewCreateButton"
import RouteStationSelect from "@/app/review/components/RouteStationSelect"
import SearchBusRoute from "@/app/review/components/SearchBusRoute"

type BusReview = components["schemas"]["BusReviewRespDto"]
type BusRoute = components["schemas"]["BusRouteRespDto"]

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
  const client = useClient()
  let routeId = searchParams?.routeId ?? undefined
  let stationId = searchParams?.stationId ?? undefined
  let timeSlot = searchParams.timeSlot ?? undefined
  let reviews: BusReview[] = []
  let busRoute: BusRoute | null = null
  if (routeId) {
    // review list
    const query = getQuery(routeId, stationId, timeSlot)
    const apiUrl = createUrl("/api/bus-review", query)
    const { data, error, response } = await client.GET("/api/bus-review", {
      params: {
        query: {
          routeId: +routeId,
          stationId: stationId ? +stationId : undefined,
          timeSlot: timeSlot ? (timeSlot as TimeSlot) : undefined,
        },
      },
    })
    if (data) {
      reviews = data
    }

    const {
      data: routeData,
      error: routeError,
      response: routeResponse,
    } = await client.GET("/api/bus/route/{id}", {
      params: {
        path: {
          id: +routeId,
        },
      },
    })
    if (routeError) {
      redirect("/review/bus")
    }
    if (routeData) {
      busRoute = routeData
    }
  }
  return (
    <BaseReviewContainer defaultTransport={"BUS"}>
      <SearchBusRoute />
      {routeId && <RouteStationSelect routeId={routeId} />}
      <BusCard busRoute={busRoute}></BusCard>
      <p className={`font-extrabold`}>리뷰 모아보기</p>
      <ReviewCreateButton />
      <BusReviewListContainer reviews={reviews} />
    </BaseReviewContainer>
  )
}
