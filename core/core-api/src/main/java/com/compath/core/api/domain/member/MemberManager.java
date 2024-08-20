package com.compath.core.api.domain.member;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.compath.core.api.controller.v1.auth.dto.SignUpRequest;
import com.compath.core.api.support.error.CoreApiException;
import com.compath.core.api.support.error.ErrorType;
import com.compath.storage.db.core.entity.location.Location;
import com.compath.storage.db.core.entity.location.LocationRepository;
import com.compath.storage.db.core.entity.member.Member;
import com.compath.storage.db.core.entity.member.MemberRepository;
import com.hwi.core.enums.SocialType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MemberManager {
	private final MemberRepository memberRepository;
	private final LocationRepository locationRepository;

	@Transactional
	public Member signUp(SignUpRequest signUpRequest, String socialId, SocialType socialType) {
		if (!signUpRequest.isTermsAgreed()) {
			throw new CoreApiException(ErrorType.BAD_REQUEST, "회원가입을 위해서 약관에 반드시 동의해야합니다.");
		}
		Member member = Member.builder()
			.name(signUpRequest.name())
			.nickname(signUpRequest.nickname())
			.email(signUpRequest.email())
			.university(signUpRequest.university())
			.college(signUpRequest.college())
			.department(signUpRequest.college())
			.studentCardImageUrl(signUpRequest.studentCardImageUrl())
			.profileImageUrl(signUpRequest.profileImageUrl())
			.socialId(socialId)
			.socialType(socialType)
			.isTermsAgreed(true)
			// .isStudentCardCertificated() // 학생증 인증 여부
			.build();
		Member savedMember = memberRepository.save(member);
		locationRepository.save(new Location(savedMember));
		return savedMember;
	}
}
