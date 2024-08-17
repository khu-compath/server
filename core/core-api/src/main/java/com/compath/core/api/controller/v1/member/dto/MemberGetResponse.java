package com.compath.core.api.controller.v1.member.dto;

import com.compath.storage.db.core.entity.Member;

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
