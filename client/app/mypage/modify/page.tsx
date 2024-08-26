"use client"

import { redirect } from "next/navigation"

import ModifyContainer from "./ModifyContainer"

export default function MyPage() {
  const isLogin = localStorage.getItem("isLogin")

  // 쿠키가 존재하지 않으면 로그인 페이지로 리다이렉트
  if (!isLogin) {
    // 리다이렉트 처리
    redirect("/login")
  }
  return (
    <div>
      <section className="container items-center justify-center pb-8 pt-6 md:py-10 min-h-full">
        <ModifyContainer />
      </section>
    </div>
  )
}
