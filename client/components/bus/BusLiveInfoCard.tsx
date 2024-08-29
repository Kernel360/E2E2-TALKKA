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
import ArrivalInfo from "@/components/bus/ArrivalInfo"
import BusSeatStatics from "@/components/bus/BusSeatStatics"

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
    <div>
      <Card className={`w-[280px] pb-5 text-center`}>
        {routeStation && (
          <div className={"flex flex-col items-center justify-center gap-y-2"}>
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
            <div className={`text-center border-0 flex flex-col items-center`}>
              <CardTitle className={"pt-5 text-xl"}>과거 통계</CardTitle>
              <CardDescription className={"pb-3"}>
                <p>{"최근 2주간의 버스 좌석 현황입니다."}</p>
                <p className={""}>{"(빨강 : 1층, 파랑 : 2층)"}</p>
              </CardDescription>
              {liveInfo?.statics && liveInfo.statics.data.length > 0 && (
                <BusSeatStatics staticsResp={liveInfo.statics} />
              )}
              {!liveInfo?.statics ||
                (liveInfo.statics.data.length === 0 && (
                  <p className={"w-[80%] border rounded-xl "}>
                    {"데이터가 없습니다. 🥲"}
                  </p>
                ))}
            </div>
          </div>
        )}
        {!routeStation && (
          <CardHeader>
            <CardDescription>{`노선 및 정류장을 입력해주세요`}</CardDescription>
          </CardHeader>
        )}
      </Card>
    </div>
  )
}
