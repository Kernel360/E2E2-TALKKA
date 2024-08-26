"use client"

import { components } from "@/api/v1"

interface BookmarkProps {
  bookmark: components["schemas"]["BookmarkRespDto"]
}

export default function Bookmark({ bookmark }: BookmarkProps) {
  // User 의 Bookmark 중 첫 번째를 보여줍니다. 없을 경우 빈 화면을 보여줍니다.
  return <div></div>
}