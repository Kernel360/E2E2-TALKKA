"use client"

import { useCallback, useEffect, useState } from "react"
import { redirect } from "next/navigation"
import useClient from "@/api/useClient"
import { components } from "@/api/v1"

import LogoutButton from "./LogoutButton"
import ModifyButton from "./ModifyButton"

type UserData = components["schemas"]["UserRespDto"]

export default function MyPage() {
  const isLogin = localStorage.getItem("isLogin")
  const [userData, setUserData] = useState<UserData | null>(null)

  if (!isLogin) {
    redirect("/logout")
    return null
  }
  // API 요청
  const client = useClient()
  const handleFetchUserData = useCallback(async () => {
    const { data, error, response } = await client.GET("/api/users/me")
    if (error) {
      switch (response.status) {
        case 401:
          redirect("/login")
          return
        default:
          console.error("Error: ", error.message)
          return
      }
    }
    setUserData(data)
  }, [])

  useEffect(() => {
    handleFetchUserData()
  }, [])

  return (
    <div className={""}>
      <section
        className="container flex flex-col items-center justify-center pb-8 pt-6 md:py-10 min-h-full
      gap-y-5 w-[300px]"
      >
        <div className="flex max-w-[980px] flex-col items-center justify-center gap-2">
          <p className="font-bold py-2">내 정보</p>
          {userData ? (
            <>
              {/*  변경 가능한 resource 만 변경할 수 있도록 함. */}
              <p>아이디: {userData.userId}</p>
              <p>이름: {userData.name}</p>
              <p>소셜 로그인 경로: {userData.oauthProvider}</p>
              <p>닉네임: {userData.nickname}</p>
              <p>이메일: {userData.email}</p>
            </>
          ) : (
            <div>로딩중...</div>
          )}
          <div className="flex gap-4">
            <ModifyButton />
            <LogoutButton />
          </div>
        </div>
      </section>
    </div>
  )
}
