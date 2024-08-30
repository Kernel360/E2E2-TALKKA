import { useCallback, useEffect, useState } from "react"
import useClient from "@/api/useClient"
import { components } from "@/api/v1"

import { Badge } from "@/components/ui/badge"
import { Button } from "@/components/ui/button"
import SearchBusRoute from "@/components/bus/SearchBusRoute"
import SelectRouteStation from "@/components/bus/SelectRouteStation"
import BusEditCard from "@/app/bookmark/components/BusEditCard"
import RouteStationCard from "@/app/bookmark/components/RouteStationCard"

type BookmarkResp = components["schemas"]["BookmarkRespDto"]
type BookmarkDetailResp = components["schemas"]["BookmarkDetailRespDto"]

type BusRoute = components["schemas"]["BusRouteRespDto"]
type BusRouteStation = components["schemas"]["BusRouteStationRespDto"]

interface EditBookmarkProps {
  bookmark: BookmarkResp
  details: BookmarkDetailResp[]
  setDetails: (details: BookmarkDetailResp[]) => void
  setEditMode: (editMode: boolean) => void
}
export default function EditBookmark({
  bookmark,
  details,
  setDetails,
  setEditMode,
}: EditBookmarkProps) {
  const [addMode, setAddMode] = useState<boolean>(false)
  const [selectedRoute, setSelectedRoute] = useState<BusRoute | null>(null)
  const [selectedRouteStation, setSelectedRouteStation] =
    useState<BusRouteStation | null>(null)
  const client = useClient()

  const handleDeleteDetail = (index: number) => {
    if (details) {
      setDetails(details.filter((_, i) => i !== index))
    }
  }

  const fetchUpdateBookmarkDetails = useCallback(async () => {
    const { response } = await client.PUT("/api/bookmark/{bookmarkId}", {
      params: {
        path: {
          bookmarkId: bookmark.id,
        },
      },
      body: {
        name: bookmark.name,
        details: details.map((detail, index) => ({
          seq: index,
          busRouteStationId: detail.routeStation.busRouteStationId,
        })),
      },
    })
    if (response.ok) {
      setEditMode(false)
    }
  }, [bookmark, details])

  const fetchDeleteBookmark = useCallback(async () => {
    const { response, error } = await client.DELETE(
      "/api/bookmark/{bookmarkId}",
      {
        params: {
          path: {
            bookmarkId: bookmark.id,
          },
        },
      }
    )
    if (response.ok) {
      setEditMode(false)
    }
  }, [bookmark])

  useEffect(() => {
    if (addMode) {
      setSelectedRoute(null)
      setSelectedRouteStation(null)
    }
  }, [addMode])
  return (
    <div
      className={`
      border rounded-xl p-5 bg-slate-50
      flex flex-col w-[100%] items-center justify-center gap-y-5 `}
    >
      {details &&
        details.map((detail, index) => (
          <>
            <BusEditCard
              key={index}
              detail={detail}
              handleDelete={() => handleDeleteDetail(index)}
            />
          </>
        ))}
      <div className={"flex flex-col items-center justify-center w-[90%]"}>
        {!addMode && (
          <Badge
            className={`bg-emerald-500 hover:bg-emerald-400 cursor-pointer`}
            onClick={() => {
              setAddMode(true)
            }}
          >
            +
          </Badge>
        )}
        {addMode && (
          <div
            className={
              "flex flex-col items-center justify-center gap-y-5 border p-2 bg-slate-100 w-[100%]"
            }
          >
            <RouteStationCard
              routeName={selectedRoute?.routeName}
              stationName={selectedRouteStation?.stationName}
            />
            <SearchBusRoute
              className={`w-[80%]`}
              setSelectedRoute={setSelectedRoute}
            />
            {selectedRoute && (
              <SelectRouteStation
                route={selectedRoute}
                setSelectedRouteStation={setSelectedRouteStation}
              />
            )}
            <div className={"flex flex-row gap-x-5 justify-between"}>
              <Button
                className={`bg-red-500 hover:bg-red-400 cursor-pointer h-[30px]`}
                onClick={() => {
                  setAddMode(false)
                }}
              >
                취소하기{" "}
              </Button>
              <Button
                className={`bg-blue-500 hover:bg-slate-400 cursor-pointer h-[30px]`}
                onClick={() => {
                  if (!selectedRouteStation) {
                    alert("노선과 정류장을 선택해주세요.")
                    return
                  }
                  setDetails([
                    ...details,
                    {
                      seq: details.length,
                      routeStation: selectedRouteStation,
                    },
                  ])
                  setSelectedRouteStation(null)
                  setSelectedRoute(null)
                  setAddMode(false)
                }}
              >
                추가하기
              </Button>
            </div>
          </div>
        )}
      </div>
      <div className={"w-full flex flex-row justify-between items-end"}>
        <Button
          className={"bg-red-500 hover:bg-red-400 h-[30px]"}
          onClick={() => {
            fetchDeleteBookmark()
          }}
        >
          북마크 삭제
        </Button>
        <Button
          className={"bg-blue-500 hover:bg-blue-400 h-[30px]"}
          onClick={() => {
            if (selectedRouteStation) {
              alert("아직 추가되지 않은 경로가 있습니다.")
              return
            }
            fetchUpdateBookmarkDetails().then(() => setEditMode(false))
          }}
        >
          수정 완료
        </Button>
      </div>
    </div>
  )
}
