package com.brainrush.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainrush.DTO.AuthLoginRequestDto;
import com.brainrush.DTO.AuthLoginResponseDto;
import com.brainrush.Service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthRestController {

	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<AuthLoginResponseDto> login(@RequestBody @Valid AuthLoginRequestDto request) {
		return ResponseEntity.ok(authService.login(request));
	}
}
