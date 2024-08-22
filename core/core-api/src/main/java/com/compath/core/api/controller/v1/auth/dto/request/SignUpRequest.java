package com.compath.core.api.controller.v1.auth.dto.request;

import com.hwi.core.enums.SocialType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignUpRequest(
	@NotBlank
	String identityToken,
	@NotNull
	SocialType socialType,
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
