"use client"

import { useState } from "react"

import { Input } from "@/components/ui/input"

import AcceptButton from "../AcceptButton"


export default function ModifyContainer() {
  const [nickname, setNickname] = useState("")

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNickname(e.target.value)
  }

  return (
    <div className="flex max-w-[980px] flex-col items-center justify-center gap-2">
      <p className="font-bold py-2">내 정보</p>
      <div>
        <Input
          type="text"
          placeholder="닉네임"
          value={nickname}
          onChange={handleInputChange}
        />
      </div>
      <div className="flex gap-4">
        <AcceptButton
          data={{
            nickname: nickname,
          }}
        />
      </div>
    </div>
  )
}
