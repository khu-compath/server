package com.compath.core.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

	@GetMapping("/")
	public ResponseEntity<Object> test() {
		return ResponseEntity.status(HttpStatus.OK).body("Welcome to Compath!");
	}

	@GetMapping("/health")
	public ResponseEntity<Object> health() {
		return ResponseEntity.status(HttpStatus.OK).body("I'm ok");
	}

	@GetMapping("favicon.ico")
	void returnNoFavicon() {
	}

}
