"use client"

import { useCallback, useEffect, useState } from "react"
import { useRouter } from "next/navigation"

import BusRouteStationResponse from "@/types/api/bus/route-station/BusRouteStationResponse"
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select"





interface RouteStationSelectProps {
  routeId: string | null
}

export default function RouteStationSelect({
  routeId,
}: RouteStationSelectProps) {
  const [stationId, setStationId] = useState<number | undefined>(undefined)
  const [stations, setStations] = useState<BusRouteStationResponse[]>([])
  const router = useRouter()
  const fetchRouteStations = useCallback(async () => {
    if (!routeId) {
      setStations([])
      return
    }
    try {
      const resp = await fetch(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/api/bus/route-station?routeId=${routeId}`,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
          credentials: "include",
        }
      )
      if (!resp.ok) {
        console.error(resp.status)
        return
      }
      const respData: BusRouteStationResponse[] = await resp.json()
      setStations(respData)
    } catch (error) {
      setStations([])
      return
    }
  }, [routeId])

  useEffect(() => {
    fetchRouteStations()
  }, [fetchRouteStations])

  useEffect(() => {
    if (stationId) {
      router.push(`bus?routeId=${routeId}&stationId=${stationId}`)
    }
  }, [stationId])

  return (
    <div className={"w-[300px]"}>
      <Select onValueChange={(value) => setStationId(parseInt(value))}>
        <SelectTrigger>
          <SelectValue placeholder={"정류장 선택"} />
        </SelectTrigger>
        <SelectContent className={"w-[300px]"}>
          {stations.map((station) => (
            <SelectItem
              value={station.busRouteStationId.toString()}
              key={station.busRouteStationId}
            >
              {station.stationName}
            </SelectItem>
          ))}
        </SelectContent>
      </Select>
    </div>
  )
}
