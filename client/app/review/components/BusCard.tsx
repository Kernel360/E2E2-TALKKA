import { components } from "@/api/v1"

import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"

interface BusCardProps {
  busRoute: components["schemas"]["BusRouteRespDto"] | null
}

export default function BusCard({ busRoute }: BusCardProps) {
  if (busRoute == null) return <></>
  return (
    <Card className={`w-[100%] text-center`}>
      {/* 가운데 정렬*/}
      <div className={"font-extrabold pt-4"}>버스 정보</div>
      <CardHeader>
        <CardTitle className={`text-3xl`}>{`${busRoute.routeName}`}</CardTitle>
        <CardDescription>{busRoute.regionName}</CardDescription>
      </CardHeader>
      <CardContent>
        <div className={"py-1"}>
          <p className={`font-bold`}>기점</p>
          <p>{`${busRoute.startStationName}`}</p>
        </div>
        <div className={"py-1"}>
          <p className={`font-bold`}>종점</p>
          <p>{`${busRoute.endStationName}`}</p>
        </div>
        <div className={"py-1"}>
          <p className={`font-bold`}>기점 운행시간</p>
          <p>{`${busRoute.upFirstTime} - ${busRoute.upLastTime}`}</p>
        </div>
        <div className={"py-1"}>
          <p className={`font-bold`}>종점 운행시간</p>
          <p>{`${busRoute.downFirstTime} - ${busRoute.downLastTime}`}</p>
        </div>
      </CardContent>
    </Card>
  )
}
