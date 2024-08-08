package com.compath.core.api.domain.auth.strategy;

import org.springframework.stereotype.Component;

import com.compath.client.oauth.OAuthMember;
import com.compath.client.oauth.apple.AppleOAuthUserProvider;
import com.compath.core.api.security.JwtTokenProvider;
import com.compath.storage.db.core.entity.Member;
import com.compath.storage.db.core.entity.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AppleSocialLoginStrategy implements SocialLoginStrategy {
	private final AppleOAuthUserProvider appleOAuthUserProvider;
	private final MemberRepository memberRepository;
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public Member loginOrSignUp(String identityToken) {
		OAuthMember oAuthMember = appleOAuthUserProvider.getAppleMember(identityToken);
		return memberRepository.findBySocialId(oAuthMember.socialId()).orElseGet(() -> {
			Member newMember = new Member(oAuthMember.socialId(), oAuthMember.email());
			return memberRepository.save(newMember);
		});
	}
}
