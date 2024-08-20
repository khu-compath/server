package com.compath.core.api.domain.member;

import org.springframework.stereotype.Component;

import com.compath.core.api.support.error.CoreApiException;
import com.compath.core.api.support.error.ErrorType;
import com.compath.storage.db.core.entity.member.Member;
import com.compath.storage.db.core.entity.member.MemberRepository;
import com.compath.storage.db.core.redis.TemporaryMemberRepository;
import com.hwi.core.enums.SocialType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MemberReader {
	private final MemberRepository memberRepository;
	private final TemporaryMemberRepository temporaryMemberRepository;

	public Member readById(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND, "Member not found"));
	}

	public Member readBySocial(String socialId, SocialType socialType) {
		return memberRepository.findBySocialIdAndSocialType(socialId, socialType)
			.orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND, "Member not found"));

	}
}
