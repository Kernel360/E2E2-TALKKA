"use client"

import { useState } from "react"
import { components } from "@/api/v1"

import BusLiveInfoCard from "@/components/bus/BusLiveInfoCard"
import SearchBusRoute from "@/components/bus/SearchBusRoute"
import SelectRouteStation from "@/components/bus/SelectRouteStation"

type BusRoute = components["schemas"]["BusRouteRespDto"]
type BusRouteStation = components["schemas"]["BusRouteStationRespDto"]

export default function IndexPage() {
  const [selectedRoute, setSelectedRoute] = useState<BusRoute | undefined>(
    undefined
  )
  const [selectedRouteStation, setSelectedRouteStation] = useState<
    BusRouteStation | undefined
  >(undefined)

  return (
    <div className={""}>
      <section
        className="container flex flex-col items-center justify-center pb-8 pt-6 md:py-10 min-h-full
      gap-y-5 w-[100%] xs:w-[500px]"
      >
        <div
          className={
            "border rounded-xl p-2 bg-slate-50 w-[100%] sm:w-[500px] min-h-[300px] flex flex-col items-center justify-center gap-y-5"
          }
        >
          <p className={"font-extrabold text-xl"}>경기도 버스 조회</p>
          <SearchBusRoute setSelectedRoute={setSelectedRoute} />
          {selectedRoute && (
            <SelectRouteStation
              route={selectedRoute}
              setSelectedRouteStation={setSelectedRouteStation}
            />
          )}
          <BusLiveInfoCard routeStation={selectedRouteStation} />
        </div>
      </section>
    </div>
  )
}
