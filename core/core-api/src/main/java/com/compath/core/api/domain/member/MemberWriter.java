package com.compath.core.api.domain.member;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.compath.core.api.controller.v1.auth.dto.SignUpRequest;
import com.compath.storage.db.core.entity.member.Member;
import com.compath.storage.db.core.entity.member.MemberRepository;
import com.compath.storage.db.core.redis.TemporaryMember;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MemberWriter {
	private final MemberRepository memberRepository;

	@Transactional
	public Member signUp(SignUpRequest signUpRequest, TemporaryMember temporaryMember) {
		Member newMember = Member.builder()
			.name(signUpRequest.name())
			.nickname(signUpRequest.nickname())
			.email(signUpRequest.email())
			.socialId(temporaryMember.getSocialId())
			.socialType(temporaryMember.getSocialType())
			.build();
		return memberRepository.save(newMember);
	}
}
