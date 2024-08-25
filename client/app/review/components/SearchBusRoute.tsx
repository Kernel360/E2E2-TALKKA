import { useState } from "react"
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
  onRouteSelect: (routeId: number) => void
  busRouteList: BusRoute[]
  searchKeyword: string
  setSearchKeyword: (value: string) => void
}

export default function SearchBusRoute({
  onRouteSelect,
  busRouteList,
  searchKeyword,
  setSearchKeyword,
}: SearchBusRouteProps) {
  const [onSearchBar, setOnSearchBar] = useState<boolean>(false)

  return (
    <>
      <Command className={`w-[300px] border`}>
        <CommandInput
          placeholder={"버스 노선을 입력하세요"}
          value={searchKeyword}
          onValueChange={(value) => {
            setSearchKeyword(value)
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
                    onRouteSelect(route.routeId)
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
