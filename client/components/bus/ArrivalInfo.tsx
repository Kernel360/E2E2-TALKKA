interface ArrivalInfo {
  className?: string
  locationNo: number | null | undefined
  predictTime: number | null | undefined
  seats: number | null | undefined
}
export default function ArrivalInfo({
  className,
  locationNo,
  predictTime,
  seats,
}: ArrivalInfo) {
  if (locationNo == null || predictTime == null) return <></>

  return (
    <p className={`${className}`}>
      <span className={`${getColor(seats) + " font-bold"}`}>{`[${
        seats == -1 ? "-" : seats
      } 석]\t`}</span>
      <span className={`${predictTime <= 5 ? "text-red-500 font-bold" : ""}`}>
        {locationNo && `${locationNo} 번째 전\t(${predictTime} 분 후)`}
      </span>
    </p>
  )
}

const getColor = (seats: number | null | undefined) => {
  if (seats == null || seats < 0) return "text-gray-500"
  return seats >= 10 ? "text-green-500" : "text-red-500"
}
