"use client"

import { useRouter } from "next/navigation"

import { Button } from "@/components/ui/button"

// NOTE: 아직 구현 제대로 안함.

export default function ReviewCreateButton() {
  const router = useRouter()
  const isLogined = localStorage.getItem("isLogin")
  if (isLogined !== "true") {
    return <></>
  }

  return <Button onClick={() => {}}>리뷰 작성하기</Button>
}

function getURL(routeId: number, stationId: number) {
  let url = `/review/bus/create`
  if (routeId) {
    url += `?routeId=${routeId}`
    if (stationId) {
      url += `&stationId=${stationId}`
    }
  }
  return url
}
