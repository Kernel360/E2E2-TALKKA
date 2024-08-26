import Link from "next/link"

import { siteConfig } from "@/config/site"
import { buttonVariants } from "@/components/ui/button"





export default function IndexPage() {
  return (
    <section className="container grid items-center gap-6 pb-8 pt-6 md:py-10">
      <div className="flex max-w-[980px] flex-col items-start gap-2">
        <h1 className="text-3xl font-extrabold leading-tight tracking-tighter md:text-4xl">
          대중교통 편하게 타자!
        </h1>
        <p className="max-w-[700px] text-lg text-muted-foreground">
          탈까? 에서는 대중교통 경로에 따른 실시간 정보와 정류소 혼잡도 정보를
          제공합니다.
        </p>
      </div>
      <div className="flex gap-4">
        <Link
          target="_blank"
          rel="noreferrer"
          href={siteConfig.links.github}
          className={buttonVariants({ variant: "outline" })}
        >
          GitHub
        </Link>
      </div>
    </section>
  )
}
