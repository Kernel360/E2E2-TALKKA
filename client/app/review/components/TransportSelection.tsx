"use client"

import { useState } from "react"
import Link from "next/link"

import { TransportType } from "@/types/api/domain/TransportType"

interface TransportSelectionProps {
  baseUrl: string
  busUrl: string
  subwayUrl: string
  defaultTransport: TransportType
}

export default function TransportSelection({
  baseUrl,
  busUrl,
  subwayUrl,
  defaultTransport,
}: TransportSelectionProps) {
  const [selectedTransport, setSelectedTransport] =
    useState<TransportType>(defaultTransport)

  return (
    <div
      className={
        "flex flex-row justify-between bg-slate-200 rounded-lg w-[80%] font-bold"
      }
    >
      <Link href={`${baseUrl + busUrl}`}>
        <button
          className={`p-2 rounded-lg w-[140px] ${
            selectedTransport === "BUS"
              ? "bg-slate-400  text-slate-50 font-extrabold"
              : ""
          }`}
        >
          버스
        </button>
      </Link>
      <Link href={`${baseUrl + subwayUrl}`}>
        <button
          className={`p-2 rounded-lg w-[140px] ${
            selectedTransport === "SUBWAY"
              ? "bg-slate-400  text-slate-50 font-extrabold"
              : ""
          }`}
        >
          지하철
        </button>
      </Link>
    </div>
  )
}
