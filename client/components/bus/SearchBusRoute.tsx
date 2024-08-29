import { useCallback, useState } from "react"
import useClient from "@/api/useClient"
import { components } from "@/api/v1"

import {
  Command,
  CommandEmpty,
  CommandInput,
  CommandItem,
  CommandList,
} from "@/components/ui/command"

type BusRoute = components["schemas"]["BusRouteRespDto"]

interface SearchBusRouteProps {
  className?: string
  setSelectedRoute: (route: BusRoute) => void
}

export default function SearchBusRoute({
  className,
  setSelectedRoute,
}: SearchBusRouteProps) {
  const [onSearchBar, setOnSearchBar] = useState<boolean>(false)
  const [searchKeyword, setSearchKeyword] = useState<string>("")
  const [busRouteList, setBusRouteList] = useState<BusRoute[]>([])
  const client = useClient()

  const fetchBusRoutes = useCallback(async () => {
    const { data, error, response } = await client.GET("/api/bus/route", {
      params: {
        query: {
          search: searchKeyword,
        },
      },
    })
    if (error) {
      setBusRouteList([])
      return
    }
    if (data && response.ok) {
      setBusRouteList(data)
    }
  }, [searchKeyword])

  return (
    <>
      <Command className={`border ${className}`}>
        <CommandInput
          placeholder={"버스 노선을 입력하세요"}
          value={searchKeyword}
          onValueChange={(value) => {
            setSearchKeyword(value)
            fetchBusRoutes()
          }}
          onFocus={() => {
            setOnSearchBar(true)
          }}
          onBlur={() => {
            // CommandList 를 건드릴 때는 무시
            setTimeout(() => {
              setOnSearchBar(false)
            }, 100)
          }}
        />
        {onSearchBar ? (
          <CommandList>
            <CommandEmpty>일치하는 버스 노선이 없습니다.</CommandEmpty>
            {busRouteList.map((route, idx) => {
              return (
                <CommandItem
                  key={idx}
                  onSelect={() => {
                    setSelectedRoute(route)
                    setSearchKeyword(route.routeName)
                  }}
                >{`${route.routeName} - ${route.regionName}`}</CommandItem>
              )
            })}
          </CommandList>
        ) : (
          <> </>
        )}
      </Command>
    </>
  )
}
