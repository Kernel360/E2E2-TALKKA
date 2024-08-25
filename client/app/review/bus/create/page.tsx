"use client"

import { useCallback, useState } from "react"
import { useRouter, useSearchParams } from "next/navigation"
import useClient from "@/api/useClient"

import { TimeSlot } from "@/types/api/domain/TimeSlot"

export default function CreateBusReviewPage() {
  const router = useRouter()
  const searchParams = useSearchParams()
  const queryRouteId = searchParams.get("routeId")
  const queryStationId = searchParams.get("stationId")
  const client = useClient()

  if (queryRouteId === null || queryStationId === null) {
    alert("잘못된 접근입니다.")
    router.push("/review/bus")
    return
  }

  const [routeId, setRouteId] = useState<number>(+queryRouteId)
  const [stationId, setStationId] = useState<number>(+queryStationId)
  const [timeSlot, setTimeSlot] = useState<TimeSlot>("T_06_00")
  const [content, setContent] = useState<string | undefined>(undefined)
  const [rating, setRating] = useState<number>(10)

  const userData = localStorage.getItem("userData")

  if (userData === null) {
    alert("로그인이 필요합니다.")
    router.push("/login")
    return
  }

  const postReview = useCallback(async () => {
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
      router.push(`/review/bus?routeId=${routeId}`)
    }
  }, [routeId, stationId])

  return (
    <section className="container items-center justify-center pb-8 pt-6 md:py-10 min-h-full">
      <div className="flex max-w-[980px] flex-col items-center justify-center gap-2">
        <p className="font-bold py-2"> 버스 리뷰 작성하기</p>
        <p>버스 리뷰 작성 페이지입니다.</p>
      </div>
    </section>
  )
}
