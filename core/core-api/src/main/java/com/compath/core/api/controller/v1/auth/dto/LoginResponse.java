package com.compath.core.api.controller.v1.auth.dto;

public record LoginResponse(
	String accessToken,
	String refreshToken
) {
	public static LoginResponse of(String accessToken, String refreshToken) {
		return new LoginResponse(accessToken, refreshToken);
	}
}
