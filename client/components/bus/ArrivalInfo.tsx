interface ArrivalInfo {
  className?: string
  locationNo: number | null | undefined
  predictTime: number | null | undefined
  predictSeat: number | null | undefined
  plateType: PlateType | null | undefined
  seats: number | null | undefined
}

type PlateType = "UNKNOWN" | "SMALL" | "MEDIUM" | "LARGE" | "DOUBLE_DECKER"
export default function ArrivalInfo({
  className,
  locationNo,
  predictTime,
  predictSeat,
  plateType,
  seats,
}: ArrivalInfo) {
  if (locationNo == null || predictTime == null) return <></>
  return (
    <p className={`${className} flex flex-col py-1`}>
      {seats !== undefined && seats !== null && (
        <div>
          <p className={`${"font-bold"}`}>
            {`[${seats == -1 ? "-" : seats}석]`}
            <span className={"text-fuchsia-700"}>
              {predictSeat !== undefined &&
                predictSeat !== null &&
                ` - [${predictSeat}석 (예측)]`}
            </span>
          </p>
        </div>
      )}
      <p className={`${predictTime <= 5 ? "text-red-500 font-bold" : ""}`}>
        {locationNo &&
          `${locationNo} 번째 전\t(${predictTime} 분 후) ${
            plateType === "DOUBLE_DECKER" ? "(2층)" : ""
          }`}
      </p>
    </p>
  )
}
