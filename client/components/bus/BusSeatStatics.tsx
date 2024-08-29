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

let currentLargeIndex = 0
let currentDoubleDeckerIndex = 0
const getColor = (plateType: PlateType, index: number) => {
  //   LARGE는 붉은색 - 노란색 계열
  //   DOUBLE_DECKER는 파란색 - 초록색 계열
  //  나머지는 검정색
  // RGB 색상으로 표현

  const redYellows = [
    "rgb(255, 0, 0)",
    "rgb(255, 50, 0)",
    "rgb(255, 100, 0)",
    "rgb(255, 150, 0)",
    "rgb(255, 200, 0)",
    "rgb(255, 200, 150)",
  ]

  const blueGreens = [
    "rgb(0, 0, 255)",
    "rgb(0, 50, 255)",
    "rgb(0, 100, 255)",
    "rgb(0, 150, 255)",
    "rgb(0, 200, 255)",
    "rgb(0, 200, 150)",
  ]

  const getColorIndex = (plateType: PlateType) => {
    if (plateType === "LARGE") {
      return currentLargeIndex++
    }
    if (plateType === "DOUBLE_DECKER") {
      return currentDoubleDeckerIndex++
    }
    return 0
  }

  if (plateType === "LARGE") {
    return "red"
    // return redYellows[getColorIndex(plateType) % redYellows.length]
  }
  if (plateType === "DOUBLE_DECKER") {
    return "blue"
    // return blueGreens[getColorIndex(plateType) % blueGreens.length]
  }
  return "black"
}

export default function BusSeatStatics({ staticsResp }: BusSeatStaticsProps) {
  if (!staticsResp || !staticsResp.data) {
    return null
  }

  const { requestTime, routeName, busNum, stationNum, stationList, data } =
    staticsResp
  /**
   * format
   * { stationName: "station1", week1: 100, week2: 200, ... },
   * { stationName: "station2", week1: 20, week2: 20, ... },
   * ...
   */

  // 00:00 형식으로 변환
  const getTimeFromDateTIme = (dateTime: string) => {
    const date = new Date(dateTime)
    const hour = `${date.getHours()}`
    const minute = `${date.getMinutes()}`.padStart(2, "0")
    return `${hour}:${minute}`
  }

  let chartConfig: ChartConfig = {}
  data.forEach((bus, index) => {
    const time =
      getTimeFromDateTIme(bus.standardTime) +
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

  const midIndex = Math.floor(stationList.length / 2)
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
    // <></>
    <ChartContainer
      config={chartConfig}
      className="min-h-[300px] w-[260px] border"
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
        legand 의 element 가 폭을 넘어가지 않고 개행되도록 함.
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
              return value.slice(0, 8)
            }
            return `${index - mid > 0 ? "+" : ""}${index - mid}`
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
  )
}
