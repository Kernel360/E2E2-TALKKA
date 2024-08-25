export type TimeSlot =
  | "T_00_00"
  | "T_00_30"
  | "T_01_00"
  | "T_01_30"
  | "T_02_00"
  | "T_02_30"
  | "T_03_00"
  | "T_03_30"
  | "T_04_00"
  | "T_04_30"
  | "T_05_00"
  | "T_05_30"
  | "T_06_00"
  | "T_06_30"
  | "T_07_00"
  | "T_07_30"
  | "T_08_00"
  | "T_08_30"
  | "T_09_00"
  | "T_09_30"
  | "T_10_00"
  | "T_10_30"
  | "T_11_00"
  | "T_11_30"
  | "T_12_00"
  | "T_12_30"
  | "T_13_00"
  | "T_13_30"
  | "T_14_00"
  | "T_14_30"
  | "T_15_00"
  | "T_15_30"
  | "T_16_00"
  | "T_16_30"
  | "T_17_00"
  | "T_17_30"
  | "T_18_00"
  | "T_18_30"
  | "T_19_00"
  | "T_19_30"
  | "T_20_00"
  | "T_20_30"
  | "T_21_00"
  | "T_21_30"
  | "T_22_00"
  | "T_22_30"
  | "T_23_00"
  | "T_23_30"

export function getValidTimeSlots(): TimeSlot[] {
  return [
    "T_00_00",
    "T_00_30",
    "T_01_00",
    "T_01_30",
    "T_02_00",
    "T_02_30",
    "T_03_00",
    "T_03_30",
    "T_04_00",
    "T_04_30",
    "T_05_00",
    "T_05_30",
    "T_06_00",
    "T_06_30",
    "T_07_00",
    "T_07_30",
    "T_08_00",
    "T_08_30",
    "T_09_00",
    "T_09_30",
    "T_10_00",
    "T_10_30",
    "T_11_00",
    "T_11_30",
    "T_12_00",
    "T_12_30",
    "T_13_00",
    "T_13_30",
    "T_14_00",
    "T_14_30",
    "T_15_00",
    "T_15_30",
    "T_16_00",
    "T_16_30",
    "T_17_00",
    "T_17_30",
    "T_18_00",
    "T_18_30",
    "T_19_00",
    "T_19_30",
    "T_20_00",
    "T_20_30",
    "T_21_00",
    "T_21_30",
    "T_22_00",
    "T_22_30",
    "T_23_00",
    "T_23_30",
  ]
}

export function getTimeSlotFromString(timeSlot: string): TimeSlot {
  const validTimeSlots: TimeSlot[] = [
    "T_00_00",
    "T_00_30",
    "T_01_00",
    "T_01_30",
    "T_02_00",
    "T_02_30",
    "T_03_00",
    "T_03_30",
    "T_04_00",
    "T_04_30",
    "T_05_00",
    "T_05_30",
    "T_06_00",
    "T_06_30",
    "T_07_00",
    "T_07_30",
    "T_08_00",
    "T_08_30",
    "T_09_00",
    "T_09_30",
    "T_10_00",
    "T_10_30",
    "T_11_00",
    "T_11_30",
    "T_12_00",
    "T_12_30",
    "T_13_00",
    "T_13_30",
    "T_14_00",
    "T_14_30",
    "T_15_00",
    "T_15_30",
    "T_16_00",
    "T_16_30",
    "T_17_00",
    "T_17_30",
    "T_18_00",
    "T_18_30",
    "T_19_00",
    "T_19_30",
    "T_20_00",
    "T_20_30",
    "T_21_00",
    "T_21_30",
    "T_22_00",
    "T_22_30",
    "T_23_00",
    "T_23_30",
  ]

  if (!validTimeSlots.includes(timeSlot as TimeSlot)) {
    throw new InvalidTimeSlotError(timeSlot)
  }

  return timeSlot as TimeSlot
}

export class InvalidTimeSlotError extends Error {
  constructor(timeSlot: string) {
    super(`Invalid time slot: ${timeSlot}`)
  }
}

export function getStringTimeSlot(timeSlot: TimeSlot) {
  return timeSlot.replace("T_", "").replace("_", ":")
}

export function getNextSlot(timeSlot: TimeSlot): TimeSlot {
  const validTimeSlots: TimeSlot[] = getValidTimeSlots()
  const currentIndex = validTimeSlots.indexOf(timeSlot)
  if (currentIndex === -1) {
    throw new InvalidTimeSlotError(timeSlot)
  }

  const nextIndex = currentIndex + 1
  if (nextIndex === validTimeSlots.length) {
    return validTimeSlots[0]
  }

  return validTimeSlots[nextIndex]
}
