"use client"

import useClient from "@/api/useClient"
import { components } from "@/api/v1"

import { Button } from "@/components/ui/button"

interface AcceptButtonProps {
  data: components["schemas"]["UserUpdateReqDto"]
}

export default function AcceptButton(props: AcceptButtonProps) {
  const client = useClient()
  const handleClick = async () => {
    const { data, response } = await client.PUT("/api/users/me", {
      body: props.data,
    })
    if (!response.ok) {
      alert("수정 실패")
      return
    }
    alert("수정 완료")
    window.location.href = `/mypage`
  }
  return <Button onClick={handleClick}>수정하기</Button>
}
