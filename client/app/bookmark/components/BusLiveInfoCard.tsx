import { useCallback, useEffect, useState } from "react"
import useClient from "@/api/useClient"
import { components } from "@/api/v1"

import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"
import ArrivalInfo from "@/app/bookmark/components/ArrivalInfo"

type RouteStation = components["schemas"]["BusRouteStationRespDto"]
type BusLiveInfoResp = components["schemas"]["BusLiveInfoRespDto"]
interface BookmarkBusCardProps {
  routeStation: RouteStation | undefined
}
export default function BusLiveInfoCard({
  routeStation,
}: BookmarkBusCardProps) {
  const { busRouteStationId, routeName, stationName } = routeStation || {}
  const [liveInfo, setLiveInfo] = useState<BusLiveInfoResp | undefined>(
    undefined
  )
  const client = useClient()

  const fetchLiveInfos = useCallback(async () => {
    if (!busRouteStationId) {
      return
    }
    const { data, error } = await client.GET("/api/bus/live/{routeStationId}", {
      params: {
        path: {
          routeStationId: busRouteStationId,
        },
      },
    })
    if (error) {
      console.error(error)
      return
    }
    if (data) {
      setLiveInfo(data)
    }
  }, [busRouteStationId])

  useEffect(() => {
    fetchLiveInfos()
  }, [busRouteStationId])

  return (
    <Card className={`w-[280px] h-[250px] text-center`}>
      {routeStation && (
        <>
          <div className={"font-extrabold pt-4"}>버스 정보</div>
          <CardHeader>
            <CardTitle className={`text-3xl`}>{`${routeName}`}</CardTitle>
            <CardDescription>{stationName}</CardDescription>
          </CardHeader>
          <CardContent>
            <div className={"py-1"}>
              {liveInfo?.arrivalInfo && (
                <>
                  <p className={`font-bold`}>도착 정보</p>
                  <ArrivalInfo
                    locationNo={liveInfo.arrivalInfo.locationNo1}
                    predictTime={liveInfo.arrivalInfo.predictTime1}
                    seats={liveInfo.arrivalInfo.remainSeatCnt1}
                  />
                  <ArrivalInfo
                    locationNo={liveInfo.arrivalInfo.locationNo2}
                    predictTime={liveInfo.arrivalInfo.predictTime2}
                    seats={liveInfo.arrivalInfo.remainSeatCnt2}
                  />
                </>
              )}
              {!liveInfo?.arrivalInfo && (
                <p className={`font-bold`}>도착 정보가 없습니다.</p>
              )}
            </div>
          </CardContent>
        </>
      )}
      {!routeStation && (
        <CardHeader>
          <CardDescription>{`노선 및 정류장을 입력해주세요`}</CardDescription>
        </CardHeader>
      )}
    </Card>
  )
}
