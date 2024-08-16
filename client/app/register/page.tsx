"use client"

import { useState } from "react"

import { Button } from "@/components/ui/button"





export default function RegisterPage() {
  const [nickname, setNickname] = useState("")

  const handleRegister = async () => {
    const res = await fetch(
      `${process.env.NEXT_PUBLIC_SERVER_URL}/api/auth/register`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ nickname }),
        credentials: "include",
      }
    )

    const json = await res.json()

    if (res.ok) {
      // 회원가입 성공 시, 로그아웃 페이지로 리다이렉트
      alert("회원가입 성공 재로그인 해주세요")
      window.location.href = `${process.env.NEXT_PUBLIC_SERVER_URL}/api/auth/logout`
    } else {
      const error = json.message
      // 실패 처리 (예: 에러 메시지 표시)
      alert(`회원가입 실패: ${error}`)
      console.error("회원가입 실패")
    }
  }

  return (
    <>
      <section className="container items-center justify-center pb-8 pt-6 md:py-10 min-h-full">
        <div className="flex max-w-[980px] flex-col items-center justify-center gap-2">
          <p className="font-bold py-2">추가 정보를 입력해주세요</p>
          {/* 닉네임 입력 */}
          <input
            type="text"
            placeholder="닉네임을 입력해주세요"
            value={nickname}
            onChange={(e) => setNickname(e.target.value)}
            className="w-full p-2 border border-gray-300 rounded-md"
          />
          {/* register 버튼 */}
          <Button onClick={handleRegister}>회원가입</Button>
        </div>
      </section>
    </>
  )
}
