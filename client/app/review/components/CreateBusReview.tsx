import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"

interface CreateBusReviewProps {
  rating: number
  setRating: (rating: number) => void
  content: string
  setContent: (content: string) => void
  fetchPostReview: () => void
}

export default function CreateBusReview({
  rating,
  setRating,
  content,
  setContent,
  fetchPostReview,
}: CreateBusReviewProps) {
  return (
    <div
      className={
        "w-[100%] flex flex-col items-center gap-y-2 bg-slate-50 border p-2 rounded-xl"
      }
    >
      <p className={"text-xl font-bold mb-2"}>리뷰등록</p>
      <span> 쾌적도 </span>
      <Input
        type={"number"}
        placeholder={"평점"}
        value={rating}
        onChange={(e) => {
          const value = +e.target.value
          if (value < 0 || value > 10) {
            return
          }
          setRating(+e.target.value)
        }}
      />
      <span> 리뷰</span>
      <Input
        type={"text"}
        placeholder={"내용"}
        value={content}
        onChange={(e) => setContent(e.target.value)}
      />
      <Button onClick={fetchPostReview}>리뷰 작성</Button>
    </div>
  )
}
