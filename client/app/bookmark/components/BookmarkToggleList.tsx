import { components } from "@/api/v1"

import { Badge } from "@/components/ui/badge"

type Bookmark = components["schemas"]["BookmarkRespDto"]

interface BookmarkToggleListProps {
  bookmarks: Bookmark[]
  selectedBookmarkIndex: number
  setSelectedBookmarkIndex: (index: number) => void
  setCreateMode: (createMode: boolean) => void
}

export default function BookmarkToggleList({
  bookmarks,
  selectedBookmarkIndex,
  setSelectedBookmarkIndex,
  setCreateMode,
}: BookmarkToggleListProps) {
  return (
    <div
      className={`flex flex-row flex-wrap items-center justify-center gap-x-2 gap-y-2`}
    >
      {bookmarks.map((bookmark, index) => (
        <Badge
          key={index}
          className={`cursor-pointer ${
            index === selectedBookmarkIndex
              ? "bg-black"
              : "bg-slate-200 text-black hover:text-white"
          }`}
          onClick={() => {
            setSelectedBookmarkIndex(index)
          }}
        >{`${bookmark.name}`}</Badge>
      ))}
      <Badge
        onClick={() => setCreateMode(true)}
        className={`cursor-pointer bg-emerald-400`}
      >
        +
      </Badge>
    </div>
  )
}
