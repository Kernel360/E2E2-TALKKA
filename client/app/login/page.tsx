import { cookies } from "next/headers"
import Image from "next/image"
import Link from "next/link"
import { redirect } from "next/navigation"

import { api } from "@/config/api"





export default function LoginPage() {
  const naverUrl = `${api.baseUrl}${api.login.naver}`
  const cookieStore = cookies()
  const token = cookieStore.get("JSESSIONID")

  if (token) {
    // 이미 로그인된 유저일 경우 로그아웃 처리를 수행한다.
    redirect("/logout")
  }

  return (
    <section className="container items-center justify-center pb-8 pt-6 md:py-10 min-h-full">
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
  )
}
