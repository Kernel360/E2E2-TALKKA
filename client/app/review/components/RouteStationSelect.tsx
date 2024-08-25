"use client"

import { useCallback, useEffect, useState } from "react"
import { useRouter } from "next/navigation"
import useClient from "@/api/useClient"
import { components } from "@/api/v1"

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

type BusRouteStation = components["schemas"]["BusRouteStationRespDto"]

export default function RouteStationSelect({
  routeId,
}: RouteStationSelectProps) {
  const [stationId, setStationId] = useState<number | undefined>(undefined)
  const [stations, setStations] = useState<BusRouteStation[]>([])
  const router = useRouter()
  const client = useClient()
  const fetchRouteStations = useCallback(async () => {
    if (!routeId) {
      setStations([])
      return
    }
    const { data, response } = await client.GET(`/api/bus/route-station`, {
      routeId: routeId,
    })
    if (!response.ok) {
      console.error(response.status)
      return
    }
    if (data) {
      setStations(data)
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
