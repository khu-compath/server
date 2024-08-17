package com.compath.core.api.domain.member;

import org.springframework.stereotype.Service;

import com.compath.core.api.controller.v1.member.dto.MemberGetResponse;
import com.compath.storage.db.core.entity.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberQueryService {
	private final MemberReader memberReader;

	public MemberGetResponse getMember(Long memberId) {
		Member member = memberReader.read(memberId);
		return MemberGetResponse.of(member);
	}
}
