"use client"

import { useCallback, useState } from "react"
import { fetch } from "next/dist/compiled/@edge-runtime/primitives"
import { useRouter, useSearchParams } from "next/navigation"
import checkLogin from "@/utils/CheckLogin"





export default function CreateBusReviewPage() {
  checkLogin()
  const router = useRouter()
  const searchParams = useSearchParams()
  const queryRouteId = searchParams.get("routeId")
  const queryStationId = searchParams.get("stationId")

  if (queryRouteId === null || queryStationId === null) {
    alert("잘못된 접근입니다.")
    router.push("/review/bus")
    return
  }

  const [routeId, setRouteId] = useState<number>(+queryRouteId)
  const [stationId, setStationId] = useState<number>(+queryStationId)

  const userData = localStorage.getItem("userData")

  if (userData === null) {
    alert("로그인이 필요합니다.")
    router.push("/login")
    return
  }

  const postReview = useCallback(async () => {
    const fetchResult = await fetch("/api/bus-review", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        routeId: routeId,
        stationId: stationId,
        content: "test",
        rating: 10,
      }),
    })
    if (fetchResult.status === 200) {
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
