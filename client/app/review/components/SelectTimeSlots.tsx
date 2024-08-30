import {
  TimeSlot,
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
  setTimeSlot: (timeSlot: TimeSlot | undefined) => void
}

export default function SelectTimeSlots({ setTimeSlot }: TimeSlotSelectProps) {
  const timeSlots: TimeSlot[] = getValidTimeSlots()

  return (
    <div className={`w-[100%]`}>
      <Select
        onValueChange={(value) => {
          if (value == "none") {
            setTimeSlot(undefined)
          } else {
            setTimeSlot(value as TimeSlot)
          }
        }}
      >
        <SelectTrigger>
          <SelectValue placeholder={"시간대 선택"} />
        </SelectTrigger>
        <SelectContent className={"w-[100%]"}>
          <SelectItem value={"none"} key={"none"}>
            {"선택안함"}
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
