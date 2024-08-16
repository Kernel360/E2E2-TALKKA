"use client"

import UserMeModifyRequest from "@/types/api/users/UserMeModifyRequest"
import { Button } from "@/components/ui/button"





interface AcceptButtonProps {
  data: UserMeModifyRequest
}

export default function AcceptButton(props: AcceptButtonProps) {
  const handleClick = async () => {
    console.log(process.env.NEXT_PUBLIC_SERVER_URL)
    const resp = await fetch(
      `${process.env.NEXT_PUBLIC_SERVER_URL}/api/users/me`,
      {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ ...props.data }),
        credentials: "include",
      }
    )
    if (resp.ok) {
      alert("수정 완료")
      window.location.href = `/mypage`
    } else {
      const errorResponse: string = await resp.json()
      alert(`수정 실패: ${errorResponse}`)
    }
  }
  return <Button onClick={handleClick}>수정하기</Button>
}
