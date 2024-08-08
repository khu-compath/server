package com.compath.core.api.domain.member;

import org.springframework.stereotype.Service;

import com.compath.core.api.support.error.CoreApiException;
import com.compath.core.api.support.error.ErrorType;
import com.compath.storage.db.core.entity.Member;
import com.compath.storage.db.core.entity.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberReader {
	private final MemberRepository memberRepository;

	public Member findMember(Long id) {
		return memberRepository.findById(id).orElseThrow(() -> new CoreApiException(ErrorType.MEMBER_NOT_FOUND));
	}
}
