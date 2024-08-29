import {
  TimeSlot,
  dateToTimeSlot,
  getStringTimeSlot,
  getValidTimeSlots,
} from "@/types/api/domain/TimeSlot"
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select"

interface TimeSlotSelectProps {
  setTimeSlot: (timeSlot: TimeSlot) => void
}

export default function SelectTimeSlots({ setTimeSlot }: TimeSlotSelectProps) {
  const timeSlots: TimeSlot[] = getValidTimeSlots()

  return (
    <div className={`w-[80%]`}>
      <Select
        onValueChange={(value) => {
          if (value.includes("now")) {
            setTimeSlot(dateToTimeSlot(new Date(Date.now())))
            return
          }
          setTimeSlot(value as TimeSlot)
        }}
      >
        <SelectTrigger>
          <SelectValue placeholder={"시간대 선택"} />
        </SelectTrigger>
        <SelectContent className={"w-[100%] max-h-[200px]"}>
          <SelectItem
            value={dateToTimeSlot(new Date(Date.now())) + "_now"}
            key={"now"}
          >
            {"현재시간"}
          </SelectItem>
          {timeSlots.map((slot) => (
            <SelectItem value={slot} key={slot}>
              {getStringTimeSlot(slot)}
            </SelectItem>
          ))}
        </SelectContent>
      </Select>
    </div>
  )
}
