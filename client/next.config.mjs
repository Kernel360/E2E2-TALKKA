/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  experimental: {
    appDir: true,
  },
  api: {
    baseUrl: "http://localhost:8080",
  },
}

export default nextConfig
