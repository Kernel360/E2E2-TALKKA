"use client"

import Image from "next/image"
import Link from "next/link"
import { redirect } from "next/navigation"

import { api } from "@/config/api"

export default function LoginPage() {
  const naverUrl = `${process.env.NEXT_PUBLIC_SERVER_URL}${api.login.naver}`
  const isLogin = localStorage.getItem("isLogin")

  if (isLogin) {
    redirect("/logout")
    return null
  }
  return (
    <div className={""}>
      <section
        className="container flex flex-col items-center justify-center pb-8 pt-6 md:py-10 min-h-full
      gap-y-5 w-[300px]"
      >
        <div className="flex max-w-[980px] flex-col items-center justify-center gap-2">
          <p className="font-bold py-2"> 탈까 로그인하기</p>
          <Link href={naverUrl}>
            <Image
              src={"/naver_login.png"}
              alt="네이버 로그인"
              width={150}
              height={50}
            />
          </Link>
        </div>
      </section>
    </div>
  )
}
