import { components } from "@/api/v1"

import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select"

interface RouteStationSelectProps {
  routeStations: BusRouteStation[]
  setStationId: (stationId: number | undefined) => void
}

type BusRouteStation = components["schemas"]["BusRouteStationRespDto"]

export default function SelectRouteStation({
  routeStations,
  setStationId,
}: RouteStationSelectProps) {
  return (
    <div className={"w-[100%]"}>
      <Select
        onValueChange={(value) => {
          if (value == "none") {
            setStationId(undefined)
            return
          }
          setStationId(parseInt(value))
        }}
      >
        <SelectTrigger>
          <SelectValue placeholder={"정류장 선택"} />
        </SelectTrigger>
        <SelectContent className={"w-[100%]"}>
          <SelectItem value={"none"} key={"none"}>
            {" "}
            {"선택안함"}{" "}
          </SelectItem>
          {routeStations.map((station) => (
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
