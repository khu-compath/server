package com.compath.core.api.controller.v1.auth.dto.request;

import jakarta.validation.constraints.Pattern;

public record EmailRequest(
	@Pattern(regexp = "^[\\w\\.-]+@khu\\.ac\\.kr$", message = "잘못된 이메일 형식입니다.")
	String email
) {
}
