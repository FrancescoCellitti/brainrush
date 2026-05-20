package com.brainrush.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainrush.DTO.AuthLoginRequestDto;
import com.brainrush.DTO.AuthLoginResponseDto;
import com.brainrush.DTO.AuthRegisterRequestDto;
import com.brainrush.DTO.AuthRegisterResponseDto;
import com.brainrush.Security.DatabaseUserDetails;
import com.brainrush.Service.RegisterService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthRestController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private RegisterService registerService;

	@PostMapping("/register")
	public ResponseEntity<AuthRegisterResponseDto> register(@RequestBody @Valid AuthRegisterRequestDto request) {
		AuthRegisterResponseDto response = registerService.register(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthLoginResponseDto> login(@RequestBody @Valid AuthLoginRequestDto request,
			HttpServletRequest httpRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);
		SecurityContextHolder.setContext(context);
		httpRequest.getSession(true)
				.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);

		Integer userId = null;
		if (authentication.getPrincipal() instanceof DatabaseUserDetails userDetails) {
			userId = userDetails.getId();
		}

		AuthLoginResponseDto response = new AuthLoginResponseDto(
				userId,
			authentication.getName(),
			authentication.getAuthorities().stream().map(a -> a.getAuthority()).toList());

		return ResponseEntity.ok(response);
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		new SecurityContextLogoutHandler().logout(request, response, authentication);
		return ResponseEntity.noContent().build();
	}
}
