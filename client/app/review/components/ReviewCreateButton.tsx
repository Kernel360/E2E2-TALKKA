"use client"

import { useRouter } from "next/navigation"
import checkLogin from "@/utils/CheckLogin"

import { Button } from "@/components/ui/button"





// NOTE: 아직 구현 제대로 안함.

export default function ReviewCreateButton() {
  checkLogin()
  const router = useRouter()
  const userData = localStorage.getItem("userData")
  if (userData === null) {
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
