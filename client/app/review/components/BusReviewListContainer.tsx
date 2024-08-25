import { components } from "@/api/v1"

import BusReview from "@/app/review/components/BusReview"

type Review = components["schemas"]["BusReviewRespDto"]
interface BusReviewListContainerProps {
  reviews: Review[]
}

export default function BusReviewListContainer({
  reviews,
}: BusReviewListContainerProps) {
  return (
    <div className={"flex flex-col items-center justify-center w-[300px] py-2"}>
      <p className={"font-bold text-xl py-2"}>리뷰 목록</p>
      {reviews.length > 0 ? (
        <div className={"flex flex-col w-[300px] gap-y-5"}>
          {reviews.map((review, idx) => {
            return <BusReview review={review} key={idx} />
          })}
        </div>
      ) : (
        <>리뷰가 없습니다.</>
      )}
    </div>
  )
}
