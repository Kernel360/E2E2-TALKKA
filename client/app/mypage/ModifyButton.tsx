"use client"

import { Button } from "@/components/ui/button"





export default function ModifyButton() {
  const handleClick = async () => {
    window.location.href = `/mypage/modify`
  }
  return <Button onClick={handleClick}>수정</Button>
}
