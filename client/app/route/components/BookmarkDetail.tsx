"use client"

import { components } from "@/api/v1"

interface BookmarkDetailProps {
  bookmarkDetail: components["schemas"]["BookmarkDetailRespDto"]
}

export default function BookmarkDetail({
  bookmarkDetail,
}: BookmarkDetailProps) {
  // route id, station id 기반으로 fetch 를 수행
  return (
    <div className={`w-[90%] bg-slate-100 rounded-xl border-2 p-2`}>
      <div>{`${bookmarkDetail.type} ${bookmarkDetail.busRouteStationId}`}</div>
    </div>
  )
}
