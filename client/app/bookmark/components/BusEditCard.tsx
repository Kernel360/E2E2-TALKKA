import { components } from "@/api/v1"

import { Badge } from "@/components/ui/badge"
import { Card, CardDescription, CardTitle } from "@/components/ui/card"

type BookmarkDetailResp = components["schemas"]["BookmarkDetailRespDto"]

interface BusDetailCardProps {
  detail: BookmarkDetailResp
  handleDelete: () => void
}

export default function BusEditCard({
  detail,
  handleDelete,
}: BusDetailCardProps) {
  return (
    <Card className={"w-[250px] h-[100px] p-2"}>
      <div className={"flex flex-row items-end justify-end"}>
        <Badge
          className={`bg-red-500 hover:bg-red-400 cursor-pointer`}
          onClick={handleDelete}
        >
          x
        </Badge>
      </div>
      <div className={"flex flex-col items-center justify-center"}>
        <CardTitle>{detail.routeStation.routeName}</CardTitle>
        <CardDescription>{detail.routeStation.stationName}</CardDescription>
      </div>
    </Card>
  )
}
