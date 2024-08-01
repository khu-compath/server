package com.compath.core.api.controller.v1.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberSignUpRequest(

	@Size(min = 3, max = 30)
	String name,

	@NotBlank
	@Email
	String email
) {
}
