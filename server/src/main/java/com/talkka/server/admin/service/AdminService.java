package com.talkka.server.admin.service;

import org.springframework.stereotype.Service;

import com.talkka.server.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	private UserService userService;

}
