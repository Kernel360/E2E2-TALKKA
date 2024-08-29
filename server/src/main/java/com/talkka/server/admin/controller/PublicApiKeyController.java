package com.talkka.server.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talkka.server.admin.exception.PublicApiKeyAlreadyExistsException;
import com.talkka.server.admin.exception.PublicApiKeyNotFoundException;
import com.talkka.server.admin.service.PublicApiKeyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api/key")
public class PublicApiKeyController {
	private final PublicApiKeyService publicApiKeyService;

	@PostMapping("")
	public ResponseEntity<?> createKey(@RequestBody String secret) {
		try {
			publicApiKeyService.createKey(secret);
		} catch (PublicApiKeyAlreadyExistsException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("")
	public ResponseEntity<?> deleteKey(@RequestBody String secret) {
		try {
			publicApiKeyService.deleteKey(secret);
		} catch (PublicApiKeyNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
}
