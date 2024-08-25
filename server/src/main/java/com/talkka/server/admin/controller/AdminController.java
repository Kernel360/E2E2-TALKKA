package com.talkka.server.admin.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.talkka.server.admin.service.AdminService;
import com.talkka.server.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
	private final AdminService adminService;
	private final UserService userService;

	@GetMapping("")
	@Secured("ADMIN")
	public String index() {
		return "admin/index";
	}

	@GetMapping("/user")
	@Secured("ADMIN")
	public String user(Model model) {
		model.addAttribute("userList", userService.getAllUser());
		return "admin/user";
	}

	@GetMapping("/review")
	@Secured(("ADMIN"))
	public String review(Model model) {
		return "admin/review";
	}

	@GetMapping("/stat")
	@Secured("ADMIN")
	public String stat(Model model) {
		return "admin/stat";
	}
}
