package com.compath.core.api.controller.v1.auth.dto;

import com.hwi.core.enums.SocialType;

public record LoginRequest(
	SocialType socialType,
	String identityToken
) {
}
