package com.compath.core.api.controller.v1.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.compath.core.api.controller.v1.auth.dto.request.EmailRequest;
import com.compath.core.api.controller.v1.auth.dto.request.LoginRequest;
import com.compath.core.api.controller.v1.auth.dto.request.SignUpRequest;
import com.compath.core.api.controller.v1.auth.dto.response.LoginResponse;
import com.compath.core.api.domain.auth.AuthService;
import com.compath.core.api.support.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "인증 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class AuthController {
	private final AuthService authService;

	@Operation(
		summary = "로그인 API",
		description = "소셜로그인 타입(KAKAO, APPLE)과 OIDC 토큰이 필요합니다. E400 코드가 반환될 경우 회원가입으로 리다이렉트가 필요합니다.")
	@PostMapping("/login")
	public ApiResponse<LoginResponse> loginWithOAuth(@RequestBody LoginRequest request) {
		final LoginResponse response = authService.loginWithOAuth(request);
		return ApiResponse.success(response);
	}

	@Operation(
		summary = "회원가입 API",
		description = "소셜로그인 타입(KAKAO, APPLE)과 OIDC 토큰이 필요합니다. 회원가입에 필요한 정보도 함께 전달해주세요.")
	@PostMapping("/signup")
	public ApiResponse<LoginResponse> signUpWithOAuth(@Valid @RequestBody SignUpRequest request) {
		final LoginResponse response = authService.signUpWithOAuth(request);
		return ApiResponse.success(response);
	}

	@PostMapping("/email")
	public ApiResponse<Void> sendEmailCode(@Valid @RequestBody EmailRequest request) {
		authService.sendEmailCode(request.email());
		return ApiResponse.success(null);
	}

	@PostMapping("/email/verify")
	public ApiResponse<Void> verifyEmailCode(@Valid @RequestBody EmailRequest request, @RequestParam String code) {
		authService.verifyEmailCode(request.email(), code);
		return ApiResponse.success(null);
	}

}
