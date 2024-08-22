"use client"

import process from "node:process"
import { useCallback, useEffect, useState } from "react"

import Bookmark from "@/app/path/components/Bookmark"





export default function PathPage() {
  // User 의 Bookmark를 들고옵니다.
  const [bookmarks, setBookmarks] = useState([])
  const fetchBookmarks = useCallback(async () => {
    const fetchResponse = await fetch(
      `${process.env.NEXT_PUBLIC_API_URL}/api/bookmarks` // bookmark page.
    )
    const resp = await fetchResponse.json()
    setBookmarks(resp.data)
  }, [bookmarks])

  useEffect(() => {
    fetchBookmarks()
  }, [fetchBookmarks])
  return <Bookmark bookmark={bookmarks[0]} />
}
