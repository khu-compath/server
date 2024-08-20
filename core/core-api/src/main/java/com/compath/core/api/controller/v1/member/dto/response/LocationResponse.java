package com.compath.core.api.controller.v1.member.dto.response;

import com.compath.storage.db.core.entity.location.Location;

public record LocationResponse(
	double latitude,
	double longitude
) {
	public static LocationResponse of(Location location) {
		return new LocationResponse(
			location.getLatitude(),
			location.getLongitude());
	}
}
