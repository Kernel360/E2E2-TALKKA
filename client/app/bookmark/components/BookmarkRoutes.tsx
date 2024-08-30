import { components } from "@/api/v1"

import BusLiveInfoCard from "@/components/bus/BusLiveInfoCard"

type BookmarkDetailResp = components["schemas"]["BookmarkDetailRespDto"]

interface BookmarkRoutesProps {
  details: BookmarkDetailResp[]
  setEditMode: (editMode: boolean) => void
}

export default function BookmarkRoutes({
  details,
  setEditMode,
}: BookmarkRoutesProps) {
  return (
    <div className={`flex flex-col gap-y-4 items-center h-full w-[100%]`}>
      {details.length === 0 && (
        <div className={"flex flex-col items-center"}>
          <p className={"font-extrabold py-2"}>북마크된 버스가 없습니다.</p>
          <p>편집하기를 눌러 버스를 추가해보세요!</p>
        </div>
      )}
      {details.map((detail, index) => (
        <BusLiveInfoCard key={index} routeStation={detail.routeStation} />
      ))}
    </div>
  )
}
