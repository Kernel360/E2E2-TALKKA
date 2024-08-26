"use client"

import { useCallback, useState } from "react"
import { useRouter } from "next/navigation"
import useClient from "@/api/useClient"

import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"

export default function CreateRoute() {
  const router = useRouter()
  const [routeName, setRouteName] = useState<string>("")
  const client = useClient()

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setRouteName(e.target.value)
  }

  const handleCreateRoute = useCallback(async () => {
    const { data, response, error } = await client.POST(`/api/bookmark`, {
      body: {
        name: routeName,
        details: [],
      },
    })
    if (error) {
      alert(error.message)
      return
    }
  }, [routeName])

  return (
    <div className={""}>
      <section className="container flex flex-col items-center justify-center p-4 gap-y-5 w-[300px]">
        <div
          className={
            "w-full flex flex-col gap-2 bg-slate-100 border rounded-xl p-2 items-center justify-center"
          }
        >
          <span className={"font-bold py-2"}>경로명</span>
          <Input
            type={"text"}
            placeholder={"경로명"}
            value={routeName}
            onChange={handleInputChange}
          />
          <Button
            className={"w-[100px]"}
            onClick={() => {
              handleCreateRoute()
              router.push("/route")
            }}
          >
            경로 생성
          </Button>
        </div>
      </section>
    </div>
  )
}
