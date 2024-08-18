package com.compath.core.api.domain.member;

import org.springframework.stereotype.Service;

import com.compath.core.api.controller.v1.member.dto.response.MemberGetResponse;
import com.compath.storage.db.core.entity.member.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberQueryService {
	private final MemberReader memberReader;

	public MemberGetResponse getMember(Long memberId) {
		Member member = memberReader.findById(memberId);
		return MemberGetResponse.of(member);
	}
}
