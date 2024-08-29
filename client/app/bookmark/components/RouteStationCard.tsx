import { Card, CardDescription, CardTitle } from "@/components/ui/card"

interface RouteStationCardProps {
  routeName?: string
  stationName?: string
}

export default function RouteStationCard({
  routeName,
  stationName,
}: RouteStationCardProps) {
  return (
    <Card className={"w-[80%] p-4 flex flex-col items-center justify-center"}>
      <CardTitle className={"text-xl"}>{`${
        routeName ? routeName : "버스를 선택해주세요"
      }`}</CardTitle>
      <CardDescription>
        {`${stationName ? stationName : "정류장을 선택해주세요"}`}{" "}
      </CardDescription>
    </Card>
  )
}
