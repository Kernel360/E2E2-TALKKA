import { cookies } from "next/headers"
import Link from "next/link"

import { siteConfig } from "@/config/site"
import { cn } from "@/lib/utils"
import { MainNav } from "@/components/main-nav"

export function SiteHeader() {
  const cookieStore = cookies()
  const sessionId = cookieStore.get("JSESSIONID")
  const isLogin = sessionId ? true : false
  console.log("isLogin", isLogin)
  return (
    <header className="bg-background sticky top-0 z-40 w-full border-b">
      <div className="container flex h-16 items-center space-x-4 justify-between sm:space-x-0 w-full text-sm">
        <MainNav items={siteConfig.mainNav} />
        <div className="flex flex-wrap items-end">
          {isLogin === false ? (
            <Link
              href={"/login"}
              className={cn(
                "flex items-center font-medium text-muted-foreground"
              )}
            >
              {"로그인"}
            </Link>
          ) : (
            <Link
              href={"/mypage"}
              className={cn(
                "flex items-center font-medium text-muted-foreground"
              )}
            >
              {"내 정보"}
            </Link>
          )}
        </div>
      </div>
    </header>
  )
}
