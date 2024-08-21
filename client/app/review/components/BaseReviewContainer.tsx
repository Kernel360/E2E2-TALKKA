import React from "react"

import { TransportType } from "@/types/api/domain/TransportType"
import TransportSelection from "@/app/review/components/TransportSelection"





interface BaseReviewContainerProps {
  defaultTransport: TransportType
  children?: React.ReactNode
}

export default function BaseReviewContainer({
  defaultTransport,
  children,
}: BaseReviewContainerProps) {
  return (
    <div>
      <section
        className="container flex flex-col items-center justify-center pb-8 pt-6 md:py-10 min-h-full
      gap-y-5"
      >
        <TransportSelection
          baseUrl={"/review"}
          busUrl={"/bus"}
          subwayUrl={"/subway"}
          defaultTransport={defaultTransport}
        />
        {children}
      </section>
    </div>
  )
}
