package com.talkka.server.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.talkka.server.admin.dto.CollectBusRouteCreateDto;
import com.talkka.server.admin.exception.CollectBusRouteAlreadyExistsException;
import com.talkka.server.admin.exception.CollectBusRouteNotFoundException;
import com.talkka.server.admin.service.AdminService;
import com.talkka.server.admin.service.CollectBusRouteService;
import com.talkka.server.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
	private final AdminService adminService;
	private final UserService userService;
	private final CollectBusRouteService collectBusRouteService;

	@GetMapping("")
	public String index() {
		return "admin/index";
	}

	@GetMapping("/user")
	public String user(Model model) {
		model.addAttribute("userList", userService.getAllUser());
		return "admin/user";
	}

	@GetMapping("/bookmark")
	public String bookmark(Model model) {
		model.addAttribute("bookmarkStats", adminService.getBookmarkStats());
		return "admin/bookmark";
	}

	@GetMapping("/review")
	public String review(Model model) {
		model.addAttribute("reviewStats", adminService.getBusReviewStats());
		return "admin/review";
	}

	@GetMapping("/collect-route")
	public String collectRoute(Model model) {
		model.addAttribute("collectRoutes", collectBusRouteService.findAllCollectBusRoutes());
		return "admin/collect-route";
	}

	@GetMapping("/collect-route/form")
	public String collectRouteForm(Model model) {
		return "admin/collect-route-form";
	}

	@PostMapping("/collect-route/form")
	public String createCollectRoute(CollectBusRouteCreateDto dto, Model model) {
		try {
			collectBusRouteService.createCollectBusRoute(dto);
		} catch (CollectBusRouteNotFoundException | CollectBusRouteAlreadyExistsException exception) {
			model.addAttribute("errorMessage", exception.getMessage());
			return "admin/collect-route-form";
		}

		return "redirect:/admin/collect-route";
	}

	@DeleteMapping("/collect-route/{collectRouteId}")
	public ResponseEntity<?> deleteCollectRoute(@PathVariable Long collectRouteId, Model model) {
		try {
			collectBusRouteService.deleteCollectBusRoute(collectRouteId);
		} catch (CollectBusRouteNotFoundException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());
		}
		return ResponseEntity.ok().build();
	}
}
