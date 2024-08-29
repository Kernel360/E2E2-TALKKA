package com.talkka.server.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	// private final PublicApiKeyService publicApiKeyService;
	// private final PublicApiKeyService publicApiKeyService;
	// private final DynamicSchedulingConfig dynamicSchedulingConfig;

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

	@GetMapping("/collect")
	public String collectRoute(Model model) {
		model.addAttribute("collectRoutes", collectBusRouteService.findAllCollectBusRoutes());
		return "admin/collect";
	}

	// @GetMapping("/key")
	// public String publicApiKey(Model model) {
	// 	model.addAttribute("apiKeys", publicApiKeyService.getKeyList());
	// 	return "admin/key";
	// }
	//
	// @GetMapping("/scheduler")
	// public String scheduler(Model model) {
	// 	var schedulers = dynamicSchedulingConfig.getSchedulers();
	// 	model.addAttribute("schedulers", schedulers);
	// 	return "admin/scheduler";
	// }
}
