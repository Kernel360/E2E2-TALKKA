"use client"

import { useEffect } from "react"
import { useRouter } from "next/navigation"

import UserResponse from "@/types/api/users/UserResponse"

export default function LoginOkPage() {
  const router = useRouter()

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const res = await fetch(
          `${process.env.NEXT_PUBLIC_SERVER_URL}/api/users/me`,
          {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
            },
            credentials: "include",
          }
        )

        if (res.status !== 200) {
          alert("로그인에 실패했습니다.")
          router.push("/login")
          return
        }
        const data: UserResponse = await res.json()
        localStorage.setItem("isLogin", "true")
        localStorage.setItem("userId", String(data.userId))
        router.push("/")
      } catch (err) {
        console.error(err)
      }
    }

    fetchUserData()
  }, [])

  return null
}
