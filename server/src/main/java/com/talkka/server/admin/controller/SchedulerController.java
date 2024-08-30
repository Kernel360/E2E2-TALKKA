package com.talkka.server.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talkka.server.admin.dto.SchedulerReqDto;
import com.talkka.server.admin.exception.InvalidCronExpressionException;
import com.talkka.server.admin.exception.SchedulerNotFoundException;
import com.talkka.server.admin.scheduler.DynamicSchedulingConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api/scheduler")
@Slf4j
public class SchedulerController {
	@Lazy
	@Autowired
	private DynamicSchedulingConfig dynamicSchedulingConfig;

	// cron 유효성 검사 로직 필요
	@PostMapping("")
	public ResponseEntity<?> updateScheduler(@RequestBody SchedulerReqDto dto) {
		try {
			dynamicSchedulingConfig.updateCronExpression(dto);
		} catch (SchedulerNotFoundException | InvalidCronExpressionException exception) {
			log.error(exception.getMessage());
			return ResponseEntity.badRequest().body(exception.getMessage());
		}
		return ResponseEntity.ok().build();
	}
}
