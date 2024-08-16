import { api } from "@/config/api"

import { FetchError } from "./fetchError"


export interface FetchResult<T> {
  data: T | null
  error: FetchError | null
}

export default async function serverFetch<BODY>(
  url: string,
  options: RequestInit
): Promise<FetchResult<BODY>> {
  try {
    const response: Response = await fetch(`${api.baseUrl + url}`, {
      ...options,
    })
    if (!response.ok) {
      const code = response.status
      switch (code) {
        case 401:
          return { data: null, error: FetchError.UNAUTHORIZED }
        case 404:
          return { data: null, error: FetchError.NOT_FOUND }
        case 403:
          return { data: null, error: FetchError.FORBIDDEN }
        case 500:
          return { data: null, error: FetchError.INTERNAL_SERVER_ERROR }
        default:
          console.error(`Error: ${code}`)
          return { data: null, error: FetchError.INTERNAL_SERVER_ERROR }
      }
    }
    const responseContent: BODY = await response.json()
    return { data: responseContent, error: null }
  } catch (e) {
    console.error("errror in server fetch: " + e)
    return { data: null, error: FetchError.INTERNAL_SERVER_ERROR }
  }
}
