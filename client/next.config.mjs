/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  experimental: {
    appDir: true,
  },
  api: {
    baseUrl: process.env.NEXT_PUBLIC_SERVER_URL,
  },
}

export default nextConfig
