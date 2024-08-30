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
  const [turnSeq, setTurnSeq] = useState<number>(0)

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

  useEffect(() => {
    if (routeStations.length > 0) {
      setTurnSeq(getTurnSeq(routeStations, route))
    }
  }, [routeStations])

  return (
    <div className={"w-[100%]"}>
      <Select
        onValueChange={(value) => {
          setSelectedRouteStation(
            routeStations.find(
              (station) => getStationName(station, turnSeq) === value
            ) as BusRouteStation
          )
        }}
      >
        <SelectTrigger className={"w-[100%]"}>
          <SelectValue placeholder={"정류장 선택"} />
        </SelectTrigger>
        <SelectContent className={"w-[90%]"}>
          {routeStations.map((station, idx) => (
            <SelectItem
              value={`${getStationName(station, turnSeq)}`}
              key={idx}
              className={"w-[250px]"}
            >
              {`${getStationName(station, turnSeq)}`}
            </SelectItem>
          ))}
        </SelectContent>
      </Select>
    </div>
  )
}

function getTurnSeq(routeStations: BusRouteStation[], route: BusRoute) {
  let tSeq = Math.floor(routeStations.length / 2)
  const endStation = route.endStationName
  for (let i = 0; i < routeStations.length; i++) {
    if (routeStations[i].stationName === endStation) {
      tSeq = i

      console.log(tSeq, routeStations[i].stationName)
      break
    }
  }

  return tSeq
}

function getStationName(station: BusRouteStation, turnSeq: number) {
  return `${station.stationName} ${
    station.stationSeq < turnSeq ? "(상행)" : "(하행)"
  }`
}
