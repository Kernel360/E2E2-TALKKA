package com.talkka.server.bus.util;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class MinuteApiCallNumberProvider implements ApiCallNumberProvider {
	/**
	 * 현재 시간을 기준으로 API 호출 번호를 반환한다.
	 * API 호출 번호는 0시 0분부터 1분 단위로 증가한다.
	 * @return API 호출 번호
	 */
	@Override
	public Integer getApiCallNumber() {
		LocalDateTime now = LocalDateTime.now();
		int hour = now.getHour();
		int minute = now.getMinute();
		return hour * 60 + minute;
	}
}
