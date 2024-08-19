package com.compath.core.api.controller.v1.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record SignUpRequest(
	@NotBlank
	String identityToken,
	String name,
	String nickname,
	String email,
	String university,
	String college,
	String department,
	String studentCardImageUrl,
	String profileImageUrl,
	boolean isTermsAgreed
) {
}
