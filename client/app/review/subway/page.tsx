import { TransportType } from "@/types/api/domain/TransportType"
import BaseReviewContainer from "@/app/review/components/BaseReviewContainer"

interface SubwayReviewPageProps {}

export default function SubwayReviewPage() {
  return (
    <BaseReviewContainer defaultTransport={TransportType.SUBWAY}>
      <h1>Subway Review Page</h1>
    </BaseReviewContainer>
  )
}
