import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select"

interface SelectWeekProps {
  setWeek: (week: number) => void
}
export default function SelectWeek({ setWeek }: SelectWeekProps) {
  // 이후에 수정할 예정...
  const weeks = [1, 2, 3, 4, 5]

  return (
    <div className={`w-[100%]`}>
      <Select
        onValueChange={(value) => {
          setWeek(+value)
        }}
      >
        <SelectTrigger>
          <SelectValue placeholder={"조회 주차 선택"} />
        </SelectTrigger>
        <SelectContent className={"w-[100%]"}>
          {weeks.map((week) => (
            <SelectItem value={week.toString()} key={week}>
              {week + "주 전"}
            </SelectItem>
          ))}
        </SelectContent>
      </Select>
    </div>
  )
}
