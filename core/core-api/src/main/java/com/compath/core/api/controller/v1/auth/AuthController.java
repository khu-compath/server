package com.compath.core.api.controller.v1.auth;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.compath.core.api.controller.v1.auth.dto.LoginRequest;
import com.compath.core.api.controller.v1.auth.dto.LoginResponse;
import com.compath.core.api.domain.auth.AuthService;
import com.compath.core.api.support.error.ErrorType;
import com.compath.core.api.support.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class AuthController {
	private final AuthService authService;

	@PostMapping("/login/oauth")
	public ApiResponse<LoginResponse> loginWithOAuth(@RequestBody LoginRequest request) {
		LoginResponse response = authService.oauthLogin(request);
		return ApiResponse.success(response);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ApiResponse<String> handlerBadCredentialsException(BadCredentialsException e) {
		return ApiResponse.error(ErrorType.BAD_REQUEST, "아이디 또는 비밀번호가 잘못되었습니다.");
	}
}
