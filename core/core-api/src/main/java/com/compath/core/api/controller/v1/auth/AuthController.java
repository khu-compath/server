package com.compath.core.api.controller.v1.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.compath.core.api.controller.v1.auth.dto.LoginRequest;
import com.compath.core.api.controller.v1.auth.dto.LoginResponse;
import com.compath.core.api.controller.v1.auth.dto.SignUpRequest;
import com.compath.core.api.domain.auth.AuthService;
import com.compath.core.api.support.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "인증 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class AuthController {
	private final AuthService authService;

	@Operation(
		summary = "로그인 API",
		description = "소셜로그인 타입(KAKAO, APPLE)과 OIDC 토큰이 필요합니다. E400 에러가 반환될 경우 회원가입 화면으로 리다이렉트 부탁드립니다.")
	@PostMapping("/login")
	public ApiResponse<LoginResponse> loginWithOAuth(@RequestBody LoginRequest request) {
		LoginResponse response = authService.loginWithOAuth(request);
		return ApiResponse.success(response);
	}

	@Operation(
		summary = "회원가입 API",
		description = "로그인을 시도했던 OIDC 토큰이 반드시 필요합니다. 회원가입에 필요한 정보도 함께 전달해주세요.")
	@PostMapping("/signup")
	public ApiResponse<LoginResponse> signUpWithOAuth(@Valid @RequestBody SignUpRequest request) {
		LoginResponse response = authService.signUpWithOAuth(request);
		return ApiResponse.success(response);
	}

}
