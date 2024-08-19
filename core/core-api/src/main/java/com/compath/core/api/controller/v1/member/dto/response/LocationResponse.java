package com.compath.core.api.controller.v1.member.dto.response;

import com.compath.storage.db.core.entity.member.MemberLocation;

public record LocationResponse(
	Long memberId,
	double latitude,
	double longitude
) {
	public static LocationResponse of(MemberLocation memberLocation) {
		return new LocationResponse(
			memberLocation.getMemberId(),
			memberLocation.getLatitude(),
			memberLocation.getLongitude());
	}
}
