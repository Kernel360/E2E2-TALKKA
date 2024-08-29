"use client"

import { useCallback, useEffect, useState } from "react"
import { useRouter } from "next/navigation"
import useClient from "@/api/useClient"
import { components } from "@/api/v1"

import { TimeSlot } from "@/types/api/domain/TimeSlot"
import BusCard from "@/app/review/components/BusCard"
import BusReviewListContainer from "@/app/review/components/BusReviewListContainer"
import CreateBusReview from "@/app/review/components/CreateBusReview"
import SearchBusRoute from "@/app/review/components/SearchBusRoute"
import SelectRouteStation from "@/app/review/components/SelectRouteStation"
import SelectTimeSlots from "@/app/review/components/SelectTimeSlots"

type BusReview = components["schemas"]["BusReviewRespDto"]
type BusRoute = components["schemas"]["BusRouteRespDto"]
type BusRouteStation = components["schemas"]["BusRouteStationRespDto"]

export default function BusReviewPage() {
  const router = useRouter()
  const client = useClient()

  const [searchKeyword, setSearchKeyword] = useState<string>("")

  const [routeId, setRouteId] = useState<number | undefined>(undefined)
  const [stationId, setStationId] = useState<number | undefined>(undefined)
  const [timeSlot, setTimeSlot] = useState<TimeSlot | undefined>(undefined)

  const [reviews, setReviews] = useState<BusReview[]>([])
  const [routes, setRoutes] = useState<BusRoute[]>([])
  const [selectedRoute, setSelectedRoute] = useState<BusRoute | undefined>(
    undefined
  )
  const [routeStations, setRouteStations] = useState<BusRouteStation[]>([])
  const [content, setContent] = useState<string>("")
  const [rating, setRating] = useState<number>(10)

  const fetchBusReviewList = useCallback(async () => {
    if (!routeId) {
      return
    }
    const { data, error, response } = await client.GET("/api/bus-review", {
      params: {
        query: {
          routeId,
          busRouteStationId: stationId,
          timeSlot,
        },
      },
    })
    if (error) {
      alert(error.message)
      return
    }
    if (data && response.ok) {
      setReviews(data)
    }
  }, [routeId, stationId, timeSlot])

  const fetchBusRoutes = useCallback(async () => {
    const { data, error, response } = await client.GET("/api/bus/route", {
      params: {
        query: {
          search: searchKeyword,
        },
      },
    })

    if (error) {
      setRoutes([])
      return
    }
    if (data && response.ok) {
      setRoutes(data)
    }
  }, [searchKeyword])

  const fetchBusRouteStations = useCallback(async () => {
    if (!routeId) {
      return
    }
    const { data, error, response } = await client.GET(
      "/api/bus/route-station",
      {
        params: {
          query: {
            routeId,
          },
        },
      }
    )
    if (error) {
      return
    }
    if (data && response.ok) {
      setRouteStations(data)
    }
  }, [routeId])

  const fetchRoute = useCallback(async () => {
    if (!routeId) {
      return
    }
    const { data, error, response } = await client.GET("/api/bus/route/{id}", {
      params: {
        path: {
          id: routeId,
        },
      },
    })
    if (error) {
      return
    }
    if (data && response.ok) {
      setSelectedRoute(data)
    }
  }, [routeId])

  const fetchPostReview = useCallback(async () => {
    if (!routeId || !stationId || !timeSlot || !rating) {
      alert("모든 항목을 입력해주세요.")
      return
    }
    const { data, error, response } = await client.POST("/api/bus-review", {
      body: {
        routeId: routeId,
        busRouteStationId: stationId,
        timeSlot: timeSlot,
        content: content,
        rating: rating,
      },
    })
    if (error) {
      alert(error.message)
      return
    }
    if (data && response.ok) {
      alert("리뷰가 등록되었습니다.")
      fetchBusReviewList()
      router.refresh()
    }
  }, [routeId, stationId, timeSlot, rating, content])

  useEffect(() => {
    fetchBusRoutes()
  }, [])

  useEffect(() => {
    fetchBusRouteStations()
    fetchRoute()
  }, [routeId])

  useEffect(() => {
    fetchBusReviewList()
  }, [routeId, stationId, timeSlot])

  console.log(reviews)

  return (
    <div className={"flex flex-col items-center gap-y-5 my-5"}>
      <p className={"font-extrabold text-xl"}>경기도 버스 조회</p>
      <div
        className={
          "border rounded-xl p-5 bg-slate-50 w-[300px] min-h-[300px] flex flex-col items-center justify-center gap-y-5"
        }
      >
        <SearchBusRoute
          onRouteSelect={setRouteId}
          busRouteList={routes}
          searchKeyword={searchKeyword}
          setSearchKeyword={setSearchKeyword}
        />
        {selectedRoute && <BusCard busRoute={selectedRoute} />}
        {routeId && (
          <SelectRouteStation
            routeStations={routeStations}
            setStationId={setStationId}
          />
        )}
        {!routeId && <div>버스 노선을 선택해주세요.</div>}
        {routeId && <SelectTimeSlots setTimeSlot={setTimeSlot} />}
        {reviews && <BusReviewListContainer reviews={reviews} />}
        {routeId && stationId && timeSlot && (
          <CreateBusReview
            rating={rating}
            setRating={setRating}
            content={content}
            setContent={setContent}
            fetchPostReview={fetchPostReview}
          />
        )}
      </div>
    </div>
  )
}
