export type SiteConfig = typeof siteConfig

export const siteConfig = {
  name: "탈까?",
  description: "대중교통 플랫폼",
  mainNav: [
    {
      title: "버스",
      href: "/",
    },
    {
      title: "북마크",
      href: "/bookmark",
    },
    {
      title: "리뷰",
      href: "/review",
    },
  ],
  links: {
    github: "https://github.com/Kernel360/E2E2-TALKKA",
    docs: "https://ui.shadcn.com",
  },
}
