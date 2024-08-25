import { components } from "@/api/v1"

import BusReview from "@/app/review/components/BusReview"

interface BusReviewListContainerProps {
  reviews: components["schemas"]["BusReviewRespDto"][]
}

export default function BusReviewListContainer({
  reviews,
}: BusReviewListContainerProps) {
  return (
    <>
      <div className={"flex flex-col w-[300px] gap-y-5"}>
        {reviews.map((review, idx) => {
          return <BusReview review={review} key={idx} />
        })}
      </div>
    </>
  )
}
