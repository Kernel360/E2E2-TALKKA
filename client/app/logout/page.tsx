"use client"

import { useEffect } from "react"

export default function LogoutPage() {
  useEffect(() => {
    localStorage.removeItem("isLogin")
    localStorage.removeItem("userId")
    window.location.href = `${process.env.NEXT_PUBLIC_SERVER_URL}/api/auth/logout`
  }, [])
  return <></> // 렌더링할 필요 없음
}
