"use client"

export default function LogoutPage() {
  window.location.href = `${process.env.NEXT_PUBLIC_SERVER_URL}/api/auth/logout`
  return <></> // 렌더링할 필요 없음
}
