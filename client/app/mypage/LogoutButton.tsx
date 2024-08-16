"use client"

import { Button } from "@/components/ui/button"





export default function LogoutButton() {
  const handleLogout = () => {
    window.location.href = `${process.env.NEXT_PUBLIC_SERVER_URL}/api/auth/logout`
  }
  return <Button onClick={handleLogout}>로그아웃</Button>
}
