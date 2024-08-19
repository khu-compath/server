package com.compath.core.api.controller.v1.member.dto.request;

public record LocationUpdateRequest(
	double latitude,
	double longitude
) {
}
