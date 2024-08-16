import BusReviewResponse from "@/types/api/bus-review/BusReviewResponse"
import { timeSlotMapping } from "@/types/api/domain/TimeSlot"
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"





interface BusReviewProps {
  review: BusReviewResponse
}

const getRatingString = (rating: number) => {
  // 별로 표현, 0.5는 빈 별로 표현
  const fullStar = "★"
  const halfStar = "☆"
  return (
    fullStar.repeat(Math.floor(rating / 2)) +
    ((rating / 2) % 1 === 0.5 ? halfStar : "")
  )
}

export default function BusReview({ review }: BusReviewProps) {
  return (
    <Card>
      <CardHeader>
        <CardTitle>{`${review.stationName}`}</CardTitle>
        <CardDescription>{`${getRatingString(review.rating)}`}</CardDescription>
        <CardDescription>{`${
          timeSlotMapping[review.timeSlot]
        }`}</CardDescription>
      </CardHeader>
      <CardContent>
        <div>{review.content}</div>
      </CardContent>
    </Card>
  )
}
