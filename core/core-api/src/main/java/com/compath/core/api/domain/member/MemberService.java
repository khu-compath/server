package com.compath.core.api.domain.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compath.core.api.controller.v1.member.dto.MemberSignUpRequest;
import com.compath.storage.db.core.entity.Member;
import com.compath.storage.db.core.entity.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public Long signUp(MemberSignUpRequest request) {
		final Member member = Member.builder()
			.name(request.name())
			.email(request.email())
			.password(passwordEncoder.encode(request.email()))
			.build();
		memberRepository.save(member);
		return member.getId();
	}
}
