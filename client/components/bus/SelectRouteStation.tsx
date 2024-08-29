import { useCallback, useEffect, useState } from "react"
import useClient from "@/api/useClient"
import { components } from "@/api/v1"

import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select"

type BusRoute = components["schemas"]["BusRouteRespDto"]
type BusRouteStation = components["schemas"]["BusRouteStationRespDto"]

interface RouteStationSelectProps {
  route: BusRoute
  setSelectedRouteStation: (routeStation: BusRouteStation) => void
}

export default function SelectRouteStation({
  route,
  setSelectedRouteStation,
}: RouteStationSelectProps) {
  const client = useClient()
  const [routeStations, setRouteStations] = useState<BusRouteStation[]>([])

  const fetchBusRouteStations = useCallback(async () => {
    if (!route) {
      return
    }
    const { data, error, response } = await client.GET(
      "/api/bus/route-station",
      {
        params: {
          query: {
            routeId: route.routeId,
          },
        },
      }
    )
    if (error) {
      return
    }
    if (data && response.ok) {
      setRouteStations(data.sort((a, b) => a.stationSeq - b.stationSeq))
    }
  }, [route])

  useEffect(() => {
    if (route) {
      fetchBusRouteStations()
    }
  }, [route])

  return (
    <div className={"w-[230px]"}>
      <Select
        onValueChange={(value) => {
          setSelectedRouteStation(
            routeStations.find(
              (station) => station.stationName === value
            ) as BusRouteStation
          )
        }}
      >
        <SelectTrigger>
          <SelectValue placeholder={"정류장 선택"} />
        </SelectTrigger>
        <SelectContent className={""}>
          {routeStations.map((station, idx) => (
            <SelectItem value={station.stationName} key={idx}>
              {station.stationName}
            </SelectItem>
          ))}
        </SelectContent>
      </Select>
    </div>
  )
}
