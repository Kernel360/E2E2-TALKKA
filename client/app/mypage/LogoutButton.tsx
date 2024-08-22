"use client"

import { useRouter } from "next/navigation"

import { Button } from "@/components/ui/button"

export default function LogoutButton() {
  const router = useRouter()
  const handleLogout = () => {
    router.push("/logout")
  }
  return <Button onClick={handleLogout}>로그아웃</Button>
}
