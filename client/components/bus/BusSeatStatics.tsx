import { components } from "@/api/v1"
import {
  LabelList,
  Line,
  LineChart,
  ReferenceLine,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts"

import { ChartConfig, ChartContainer } from "@/components/ui/chart"

type Statics = components["schemas"]["BusStaticsDto"]
type PlateType = "UNKNOWN" | "SMALL" | "MEDIUM" | "LARGE" | "DOUBLE_DECKER"

interface BusSeatStaticsProps {
  staticsResp: Statics
}

const getColor = (plateType: PlateType) => {
  if (plateType === "LARGE") {
    return "#fa9494"
  }
  if (plateType === "DOUBLE_DECKER") {
    return "#33a3ff"
  }
  return "black"
}

// 00:00 형식으로 변환
const getTimeFromDateTIme = (dateTime: string) => {
  const date = new Date(dateTime)
  const month = `${date.getMonth() + 1}`.padStart(2, "0")
  const day = `${date.getDate()}`.padStart(2, "0")
  const hour = `${date.getHours()}`.padStart(2, "0")
  const minute = `${date.getMinutes()}`.padStart(2, "0")
  return `${month}-${day} ${hour}:${minute}`
}

export default function BusSeatStatics({ staticsResp }: BusSeatStaticsProps) {
  if (!staticsResp || !staticsResp.data) {
    return null
  }

  const { requestTime, stationList, data } = staticsResp
  /**
   * format
   * { stationName: "station1", week1: 100, week2: 200, ... },
   * { stationName: "station2", week1: 20, week2: 20, ... },
   * ...
   */

  const midIndex = Math.floor(stationList.length / 2)

  let chartConfig: ChartConfig = {}
  data.forEach((bus, index) => {
    const time =
      getTimeFromDateTIme(bus.remainSeatList[midIndex].arrivedTime) +
      (bus?.plateType === "DOUBLE_DECKER" ? " (2층)" : " (1층)")
    chartConfig[time] = {
      label: time,
      color: getColor(bus.plateType, index),
    }
  })

  const chartData = stationList.map((station, i) => {
    const result: any = {
      stationName: station.stationName,
    }
    let x = 0

    for (const [key, value] of Object.entries(chartConfig)) {
      result[key] = data[x].remainSeatList[i].remainSeat
      ++x
    }
    return result
  })

  // 5 단위로 표시
  let min = data
    .map((bus) => bus.remainSeatList.map((seat) => seat.remainSeat))
    .flat()
    .reduce((a, b) => Math.min(a, b))
  min = Math.floor((min - 10) / 5) * 5
  let max = data
    .map((bus) => bus.remainSeatList.map((seat) => seat.remainSeat))
    .flat()
    .reduce((a, b) => Math.max(a, b))
  max = Math.ceil((max + 10) / 5) * 5

  const dataKeys = Object.keys(chartData[0]).filter(
    (key) => key !== "stationName"
  )

  let minIndex = 0
  let maxIndex = 0
  let minSeat = 100
  let maxSeat = 0
  for (let i = 0; i < data.length; i++) {
    if (
      data[i]?.remainSeatList[midIndex] &&
      data[i]?.remainSeatList[midIndex].remainSeat < minSeat
    ) {
      minSeat = data[i]?.remainSeatList[midIndex].remainSeat
      minIndex = i
    }
    if (
      data[i]?.remainSeatList[midIndex] &&
      data[i]?.remainSeatList[midIndex].remainSeat > maxSeat
    ) {
      maxSeat = data[i]?.remainSeatList[midIndex].remainSeat
      maxIndex = i
    }
  }

  return (
    <div className={"flex flex-col items-center gap-y-2"}>
      <p className={"font-light text-sm"}>
        {`기준 시간: ${dateTimeToString(requestTime)}`}
      </p>
      <ChartContainer
        config={chartConfig}
        className="min-h-[300px] w-[240px] sm:w-[400px]"
      >
        <LineChart
          accessibilityLayer
          data={chartData}
          margin={{
            top: 20,
            right: 20,
            left: 20,
            bottom: 20,
          }}
        >
          {/*legand 의 element 가 폭을 넘어가지 않고 개행되도록 함.*/}
          {/*<ChartLegend*/}
          {/*  content={*/}
          {/*    <ChartLegendContent*/}
          {/*      payload={[*/}
          {/*        { value: "1층", type: "circle", color: "black" },*/}
          {/*        { value: "2층", type: "circle", color: "blue" },*/}
          {/*      ]}*/}
          {/*    />*/}
          {/*  }*/}
          {/*  wrapperStyle={{*/}
          {/*    width: "100%",*/}
          {/*    display: "flex",*/}
          {/*    flexWrap: "wrap",*/}
          {/*    justifyContent: "center",*/}
          {/*  }}*/}
          {/*/>*/}
          <XAxis
            dataKey={"stationName"}
            tickLine={true}
            tickMargin={5}
            width={0.8}
            axisLine={true}
            tickFormatter={(value, index) => {
              const mid = Math.floor(stationList.length / 2)
              if (index === mid) {
                return "현위치"
                // return value.slice(0, 8)
              }
              const repeat = Math.abs(index - mid)
              return `${
                index - mid > 0
                  ? "다".repeat(repeat) + "음"
                  : "전".repeat(repeat)
              }`
            }}
          />
          <YAxis axisLine={false} hide domain={[min, max]} />
          {stationList?.length && (
            <ReferenceLine
              x={stationList[Math.floor(stationList.length / 2)].stationName}
              stroke="black"
            />
          )}
          {dataKeys.map((key, index) => (
            <>
              <Tooltip />
              <Line
                dataKey={key}
                fill={chartConfig[key].color}
                stroke={chartConfig[key].color}
              >
                {index === minIndex && (
                  <LabelList
                    position={"bottom"}
                    offset={12}
                    className={"fill-foreground font-bold"}
                    fontSize={12}
                  />
                )}
                {index === maxIndex && index !== minIndex && (
                  <LabelList
                    position={"top"}
                    offset={12}
                    className={"fill-foreground font-bold"}
                    fontSize={12}
                  />
                )}
              </Line>
            </>
          ))}
        </LineChart>
      </ChartContainer>
    </div>
  )
}

function dateTimeToString(dateTime: string) {
  const date = new Date(dateTime)
  const hour = `${date.getHours()}`
  const minute = `${date.getMinutes()}`.padStart(2, "0")
  return `${hour}:${minute}`
}
