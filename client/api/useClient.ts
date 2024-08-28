import { paths } from "@/api/v1"
import createClient from "openapi-fetch"

export default function useClient() {
  return createClient<paths>({
    baseUrl: process.env.NEXT_PUBLIC_SERVER_URL,
    credentials: "include",
  })
}
