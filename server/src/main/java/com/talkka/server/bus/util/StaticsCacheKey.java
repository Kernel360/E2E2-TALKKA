package com.talkka.server.bus.util;

import java.time.LocalDateTime;

public record StaticsCacheKey(
	Long routeStationId,
	Integer stationNum,
	LocalDateTime time,
	Integer timeRangeMinute,
	Long week
) {
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		StaticsCacheKey that = (StaticsCacheKey)o;
		return week.equals(that.week) && stationNum.equals(that.stationNum) && time.equals(that.time)
			&& routeStationId.equals(that.routeStationId) && timeRangeMinute.equals(that.timeRangeMinute);
	}

	@Override
	public int hashCode() {
		int result = routeStationId.hashCode();
		result = 31 * result + stationNum.hashCode();
		result = 31 * result + time.hashCode();
		result = 31 * result + timeRangeMinute.hashCode();
		result = 31 * result + week.hashCode();
		return result;
	}
}
