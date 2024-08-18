package com.compath.core.api.controller.v1.member.dto.response;

import com.compath.storage.db.core.entity.member.Member;

public record MemberGetResponse(
	Long memberId,
	String name,
	String nickname,
	String university,
	String college,
	String department,
	String role
) {
	public static MemberGetResponse of(Member member) {
		return new MemberGetResponse(
			member.getId(),
			member.getName(),
			member.getNickname(),
			member.getUniversity(),
			member.getCollege(),
			member.getDepartment(),
			member.getRole().toString()
		);
	}
}
