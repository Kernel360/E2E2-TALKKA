"use client"

import { useCallback, useEffect, useState } from "react"
import { redirect } from "next/navigation"
import useClient from "@/api/useClient"
import { components } from "@/api/v1"

import BookmarkRoutes from "@/app/bookmark/components/BookmarkRoutes"
import BookmarkToggleList from "@/app/bookmark/components/BookmarkToggleList"
import CreateBookmark from "@/app/bookmark/components/CreateBookmark"
import EditBookmark from "@/app/bookmark/components/EditBookmark"
import EditButton from "@/app/bookmark/components/EditButton"
import RefreshButton from "@/app/bookmark/components/RefreshButton"

type BookmarkResp = components["schemas"]["BookmarkRespDto"]
type BookmarkDetailResp = components["schemas"]["BookmarkDetailRespDto"]

export default function PathPage() {
  const isLogined = localStorage.getItem("isLogin")

  const client = useClient()
  const [bookmarks, setBookmarks] = useState<BookmarkResp[]>([])
  const [details, setDetails] = useState<BookmarkDetailResp[]>([])
  const [selectedBookmarkIndex, setSelectedBookmarkIndex] = useState<number>(0)
  const [createMode, setCreateMode] = useState<boolean>(false)
  const [editMode, setEditMode] = useState<boolean>(false)
  const [refreshMode, setRefreshMode] = useState<boolean>(false)

  const fetchBookmarks = useCallback(async () => {
    // 불필요한 fetch 를 방지하기 위해 createMode, editMode 일 때는 fetch 하지 않습니다.
    if (createMode || editMode) {
      return
    }
    setSelectedBookmarkIndex(0)
    const { data, response } = await client.GET("/api/bookmark")
    if (!response.ok || !data) {
      return
    }
    setBookmarks(data)
  }, [createMode, editMode])

  useEffect(() => {
    if (createMode || editMode) {
      return
    }
    fetchBookmarks()
  }, [editMode, createMode])

  useEffect(() => {
    if (
      selectedBookmarkIndex < 0 ||
      selectedBookmarkIndex >= bookmarks.length
    ) {
      return
    }
    if (!refreshMode) {
      setRefreshMode(false)
    }
    if (!editMode) {
      setDetails(bookmarks[selectedBookmarkIndex].details)
    }
    setRefreshMode(false)
  }, [bookmarks, selectedBookmarkIndex, refreshMode, details, editMode])

  useEffect(() => {
    if (!isLogined) {
      redirect("/logout")
      return
    }
  }, [])

  const handleRefresh = () => {
    setDetails([])
    setRefreshMode(true)
  }
  if (!isLogined) {
    redirect("/logout")
    return null
  }

  return (
    <div className={""}>
      <section className="container flex flex-col items-center justify-center pb-8 pt-6 md:py-10 min-h-full gap-y-5 w-[100%]">
        <div>
          <p className="font-bold py-2 text-xl">버스 북마크</p>
        </div>
        {createMode && !editMode && (
          <CreateBookmark setCreateMode={setCreateMode} />
        )}
        {editMode && !createMode && (
          <EditBookmark
            setEditMode={setEditMode}
            details={details}
            setDetails={setDetails}
            bookmark={bookmarks[selectedBookmarkIndex]}
          />
        )}
        {!createMode && !editMode && bookmarks && (
          <div className="w-[100%] flex flex-col items-center justify-center gap-y-5">
            <BookmarkToggleList
              bookmarks={bookmarks}
              selectedBookmarkIndex={selectedBookmarkIndex}
              setSelectedBookmarkIndex={setSelectedBookmarkIndex}
              setCreateMode={setCreateMode}
            />
            <div
              className={
                "border rounded-xl p-2 bg-slate-50 w-[100%] sm:w-[500px] min-h-[300px] flex flex-col items-center justify-center gap-y-5"
              }
            >
              {bookmarks.length === 0 && (
                <div className={"font-extrabold"}>
                  경로에 대한 북마크를 추가해주세요!
                </div>
              )}
              {bookmarks.length > 0 && (
                <>
                  <BookmarkRoutes details={details} setEditMode={setEditMode} />
                  <div
                    className={"w-full flex flex-row justify-between items-end"}
                  >
                    <EditButton setEditMode={setEditMode} />
                    <RefreshButton handleRefresh={handleRefresh} />
                  </div>
                </>
              )}
            </div>
          </div>
        )}
      </section>
    </div>
  )
}
