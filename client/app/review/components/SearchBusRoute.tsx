"use client"

import { useCallback, useEffect, useState } from "react"
import { useRouter } from "next/navigation"
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

interface SearchBusRouteProps {}

export default function SearchBusRoute() {
  const [searchKeyword, setSearchKeyword] = useState<string>("")
  const [busRouteList, setBusRouteList] = useState<BusRoute[]>([])
  const [onSearchBar, setOnSearchBar] = useState<boolean>(false)
  const router = useRouter()
  const client = useClient()
  const fetchSearch = useCallback(async () => {
    if (searchKeyword.length == 0) {
      setBusRouteList([])
      return
    }
    const { data, response } = await client.GET(`/api/bus/route`, {
      search: searchKeyword,
    })
    if (response.status !== 200) {
      console.error(response.status)
      return
    }
    if (data) {
      setBusRouteList(data)
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
