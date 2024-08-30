package com.talkka.server.bus.controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talkka.server.admin.scheduler.DynamicScheduled;
import com.talkka.server.bus.service.BusLocationProcessor;
import com.talkka.server.bus.service.BusLocationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bus/process")
public class BusLocationProcessController {
	private final BusLocationProcessor busLocationProcessor;
	private final BusLocationService busLocationService;

	@PostMapping("/all")
	public void processAll() {
		busLocationProcessor.start(busLocationService.findAll());
	}

	@DynamicScheduled(name = "data_processing", cron = "0 0 3 * * *")
	@PostMapping("/today")
	public void processToday() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime end = now.withHour(3).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime start = end.minusDays(1);
		busLocationProcessor.start(busLocationService.findByPeriod(start, end));
	}
}
