"use client"

import { redirect } from "next/navigation"

import ModifyContainer from "./ModifyContainer"

export default function MyPage() {
  const isLogin = localStorage.getItem("isLogin")

  // 쿠키가 존재하지 않으면 로그인 페이지로 리다이렉트
  if (!isLogin) {
    redirect("/login")
  }
  return (
    <div className={""}>
      <section
        className="container flex flex-col items-center justify-center pb-8 pt-6 md:py-10 min-h-full
      gap-y-5 w-[100%]"
      >
        <ModifyContainer />
      </section>
    </div>
  )
}
