package com.talkka.server.bus.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talkka.server.bus.dao.BusLocationRepository;
import com.talkka.server.bus.service.BusLocationProcessor;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bus/stat")
public class BusLocationProcessController {
	private final BusLocationProcessor busLocationProcessor;
	private final BusLocationRepository busLocationRepository;

	@PostMapping("/process/all")
	public String process() {
		busLocationProcessor.start(busLocationRepository.findAll());
		return "success";
	}
}
