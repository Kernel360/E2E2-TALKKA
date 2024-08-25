"use client"

import { useCallback, useEffect, useState } from "react"
import { useRouter, useSearchParams } from "next/navigation"
import useClient from "@/api/useClient"
import { components } from "@/api/v1"

import { Badge } from "@/components/ui/badge"
import { Button } from "@/components/ui/button"
import BookmarkDetail from "@/app/route/components/BookmarkDetail"
import CreateRoute from "@/app/route/components/CreateRoute"

type Bookmark = components["schemas"]["BookmarkRespDto"]
type BookmarkDetail = components["schemas"]["BookmarkDetailRespDto"]

export default function PathPage() {
  const router = useRouter()
  const searchParams = useSearchParams()
  const selected = +(searchParams.get("bookmark") ?? 0)
  const createMode = searchParams.get("create") === "true" ?? false
  const editMode = searchParams.get("edit") === "true" ?? false
  const [insertMode, setInsertMode] = useState<boolean>(false)

  const client = useClient()
  // User 의 Bookmark를 들고옵니다.
  const [bookmarks, setBookmarks] = useState<Bookmark[]>([])
  const [details, setDetails] = useState<BookmarkDetail[]>([])
  const [isLoading, setIsLoading] = useState<boolean>(false)
  const [insertList, setInsertList] = useState<any>([])

  const fetchBookmarks = useCallback(async () => {
    setIsLoading(true)
    const { data, response, error } = await client.GET("/api/bookmark")
    setIsLoading(false)
    if (data) {
      setBookmarks(data)
    }
    // setPath(getMockBookmarkResp())
  }, [])

  const handleEdit = () => {
    const bookmarkId = bookmarks[selected].id
  }

  useEffect(() => {
    fetchBookmarks()
  }, [createMode, editMode])

  useEffect(() => {
    if (bookmarks.length > 0) {
      setDetails(bookmarks[selected].details) // 첫 번째 북마크의 세부 사항을 설정
      setInsertList(bookmarks[selected].details)
    }
  }, [bookmarks])

  return (
    <div className={""}>
      <section
        className="container flex flex-col items-center justify-center pb-8 pt-6 md:py-10 min-h-full
      gap-y-5 w-[300px]"
      >
        {isLoading ? (
          <div> 로딩 중... </div>
        ) : (
          <>
            {createMode ? (
              <CreateRoute />
            ) : (
              <>
                <div
                  className={`flex flex-row flex-wrap items-center justify-center gap-x-2 gap-y-2`}
                >
                  {bookmarks.map((bookmark, index) => (
                    <Badge
                      key={index}
                      className={`cursor-pointer ${
                        index === selected
                          ? "bg-black"
                          : "bg-slate-200 text-black hover:text-white"
                      }`}
                      onClick={() => {
                        setDetails(bookmarks[index].details)
                        router.push(`/route?bookmark=${index}&edit=${editMode}`)
                      }}
                    >{`${bookmark.name}`}</Badge>
                  ))}
                  <Badge
                    onClick={() => router.push("?create=true")}
                    className={`cursor-pointer bg-emerald-400`}
                  >
                    +
                  </Badge>
                </div>
                {/**/}
                <div
                  className={
                    "flex flex-col items-center w-[280px] bg-slate-50 rounded-xl border gap-y-2 p-2"
                  }
                >
                  {editMode ? (
                    <>
                      {
                        //   detail list 를 추가합니다..
                      }
                      <Badge
                        className={`bg-emerald-500 hover:bg-emerald-400`}
                        onClick={() => {
                          setInsertMode(true)
                        }}
                      >
                        +
                      </Badge>
                      <Button
                        className={"bg-red-500 hover:bg-red-400 h-[30px]"}
                        onClick={() => {
                          router.push(`?bookmark=${selected}&edit=false`)
                        }}
                      >
                        수정 완료
                      </Button>
                    </>
                  ) : (
                    <>
                      {details
                        .sort((a, b) => a.seq - b.seq)
                        .map((detail, index) => (
                          <BookmarkDetail bookmarkDetail={detail} key={index} />
                        ))}
                      <Button
                        className={
                          "bg-emerald-500 hover:bg-emerald-400  h-[30px]"
                        }
                        onClick={() => {
                          router.push(`?bookmark=${selected}&edit=true`)
                        }}
                      >
                        편집하기
                      </Button>
                    </>
                  )}
                </div>
              </>
            )}
          </>
        )}
      </section>
    </div>
  )
}
