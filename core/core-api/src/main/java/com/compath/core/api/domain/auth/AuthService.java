package com.compath.core.api.domain.auth;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compath.core.api.controller.v1.auth.dto.LoginRequest;
import com.compath.core.api.controller.v1.auth.dto.LoginResponse;
import com.compath.core.api.oauth.OAuthMember;
import com.compath.core.api.oauth.OIDCUserService;
import com.compath.core.api.security.JwtTokenProvider;
import com.compath.core.api.security.UserDetailsImpl;
import com.compath.storage.db.core.entity.Member;
import com.compath.storage.db.core.entity.MemberRepository;
import com.hwi.core.enums.member.SocialType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthService {
	private final OIDCUserService oidcUserService;
	private final JwtTokenProvider jwtTokenProvider;
	private final MemberRepository memberRepository;

	public LoginResponse oauthLogin(LoginRequest request) {
		Member member = loginOrSignUp(request.socialType(), request.identityToken());
		UserDetails userDetails = UserDetailsImpl.builder()
			.id(member.getId())
			.password(member.getPassword())
			.authorities(List.of(new SimpleGrantedAuthority(member.getRole().getValue())))
			.build();
		String accessToken = jwtTokenProvider.createAccessToken(userDetails);
		return LoginResponse.of(accessToken, null);
	}

	public Member loginOrSignUp(SocialType socialType, String identityToken) {
		OAuthMember oAuthMember = oidcUserService.getOIDCMember(socialType, identityToken);
		return memberRepository.findBySocialId(oAuthMember.socialId()).orElseGet(() -> {
			Member newMember = new Member(oAuthMember.socialId(), oAuthMember.email());
			return memberRepository.save(newMember);
		});
	}
}
