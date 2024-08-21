"use client"

import { useCallback, useEffect, useState } from "react"
import { useRouter } from "next/navigation"

import {
  BusRouteResponse,
  mockBusRoutes,
} from "@/types/api/bus/route/BusRouteResponse"
import {
  Command,
  CommandEmpty,
  CommandInput,
  CommandItem,
  CommandList,
} from "@/components/ui/command"





interface SearchBusRouteProps {}

export default function SearchBusRoute() {
  const [searchKeyword, setSearchKeyword] = useState<string>("")
  const [busRouteList, setBusRouteList] = useState<BusRouteResponse[]>([])
  const [onSearchBar, setOnSearchBar] = useState<boolean>(false)
  const router = useRouter()
  const fetchSearch = useCallback(async () => {
    if (searchKeyword.length == 0) {
      setBusRouteList([])
      return
    }
    try {
      const resp = await fetch(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/api/bus/route?search=${searchKeyword}`,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
          credentials: "include",
        }
      )
      if (!resp.ok) {
        setBusRouteList(mockBusRoutes)
        console.error(resp.status)
        return
      }

      const respData: BusRouteResponse[] = await resp.json()
      setBusRouteList(respData)
    } catch (error) {
      setBusRouteList([])
      return
    }
  }, [searchKeyword])

  useEffect(() => {
    fetchSearch()
  }, [fetchSearch])
  return (
    <>
      <Command className={`w-[300px] border`}>
        <CommandInput
          placeholder={"버스 노선을 입력하세요"}
          value={searchKeyword}
          onValueChange={(value) => setSearchKeyword(value)}
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
            {busRouteList.map((busRoute, idx) => {
              return (
                <CommandItem
                  key={idx}
                  onSelect={() => {
                    router.push(`?routeId=${busRoute.routeId}`)
                  }}
                >{`${busRoute.routeName} - ${busRoute.regionName}`}</CommandItem>
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