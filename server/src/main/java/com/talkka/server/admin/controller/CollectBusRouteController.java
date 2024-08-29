package com.talkka.server.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talkka.server.admin.dto.CollectBusRouteCreateDto;
import com.talkka.server.admin.exception.CollectBusRouteAlreadyExistsException;
import com.talkka.server.admin.exception.CollectBusRouteNotFoundException;
import com.talkka.server.admin.service.CollectBusRouteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api/collect")
public class CollectBusRouteController {
	private final CollectBusRouteService collectBusRouteService;

	@PostMapping("")
	public ResponseEntity<?> createCollectRoute(@RequestBody CollectBusRouteCreateDto dto) {
		try {
			collectBusRouteService.createCollectBusRoute(dto);
		} catch (CollectBusRouteNotFoundException | CollectBusRouteAlreadyExistsException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());
		}
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{collectRouteId}")
	public ResponseEntity<?> deleteCollectRoute(@PathVariable Long collectRouteId) {
		try {
			collectBusRouteService.deleteCollectBusRoute(collectRouteId);
		} catch (CollectBusRouteNotFoundException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());
		}
		return ResponseEntity.ok().build();
	}
}
