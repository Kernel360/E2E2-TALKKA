import { useCallback, useState } from "react"
import useClient from "@/api/useClient"

import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"

interface CreateRouteProps {
  setCreateMode: (createMode: boolean) => void
}

export default function CreateBookmark({ setCreateMode }: CreateRouteProps) {
  const [routeName, setRouteName] = useState<string>("")
  const client = useClient()

  const fetchCreateBookmark = useCallback(async (bookmarkName: string) => {
    const { response, error } = await client.POST("/api/bookmark", {
      body: { name: bookmarkName, details: [] },
    })
    if (error) {
      alert(`북마크 생성에 실패했습니다. ${error.message}`)
      return
    }
    if (response.ok) {
      setCreateMode(false)
    }
  }, [])

  return (
    <div className={""}>
      <section className="container flex flex-col items-center justify-center p-4 gap-y-5 w-[100%]">
        <div
          className={
            "w-[100%] flex flex-col gap-2 bg-slate-100 border rounded-xl p-2 items-center justify-center"
          }
        >
          <span className={"font-bold py-2"}>경로명</span>
          <Input
            type={"text"}
            placeholder={"경로명"}
            value={routeName}
            onChange={(e) => {
              setRouteName(e.target.value)
            }}
          />
          <Button
            className={"w-[100px]"}
            onClick={() => {
              fetchCreateBookmark(routeName)
            }}
          >
            경로 생성
          </Button>
        </div>
      </section>
    </div>
  )
}
