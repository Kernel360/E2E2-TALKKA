// app/mypage/page.js

import { cookies } from "next/headers"
import { redirect } from "next/navigation"
import { FetchError } from "@/utils/fetchError"
import serverFetch, { FetchResult } from "@/utils/serverFetch"

import UserResponse from "@/types/api/users/UserResponse"
import { api } from "@/config/api"

import LogoutButton from "./LogoutButton"
import ModifyButton from "./ModifyButton"


export default async function MyPage() {
  const cookieStore = cookies()
  const token = cookieStore.get("JSESSIONID")
  // 쿠키가 존재하지 않으면 로그인 페이지로 리다이렉트
  if (!token) {
    // 리다이렉트 처리
    redirect("/login")
  }

  // API 요청
  const resp: FetchResult<UserResponse> = await serverFetch(
    `${api.baseUrl}/api/users/me`,
    {
      headers: {
        Cookie: `JSESSIONID=${token.value}`, // 필요한 쿠키 포함
      },
    }
  )
  if (resp.error) {
    switch (resp.error) {
      case FetchError.UNAUTHORIZED:
        redirect("/login")
        break
      default:
        console.error("Error: ", resp.error)
        break
    }
  }
  const myData = resp.data

  return (
    <div>
      <section className="container items-center justify-center pb-8 pt-6 md:py-10 min-h-full">
        <div className="flex max-w-[980px] flex-col items-center justify-center gap-2">
          <p className="font-bold py-2">내 정보</p>
          {myData !== null && (
            <>
              {/*  변경 가능한 resource 만 변경할 수 있도록 함. */}
              <p>아이디: {myData.userId}</p>
              <p>이름: {myData.name}</p>
              <p>소셜 로그인 경로: {myData.oauthProvider}</p>
              <p>닉네임: {myData.nickname}</p>
              <p>이메일: {myData.email}</p>
            </>
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
