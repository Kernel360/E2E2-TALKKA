"use client"

import { useEffect } from "react"
import { useRouter } from "next/navigation"
import useClient from "@/api/useClient"

export default function LoginOkPage() {
  const router = useRouter()
  const client = useClient()

  useEffect(() => {
    const fetchUserData = async () => {
      const { data, error } = await client.GET("/api/users/me")
      if (error) {
        alert("로그인에 실패했습니다.")
        router.push("/login")
        return
      }
      localStorage.setItem("isLogin", "true")
      localStorage.setItem("userId", String(data.userId))
      router.push("/")
    }
    fetchUserData()
  }, [])

  return null
}
