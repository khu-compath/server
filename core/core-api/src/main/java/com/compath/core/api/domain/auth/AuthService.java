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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthService {
	private final OIDCUserService oidcUserService;
	private final JwtTokenProvider jwtTokenProvider;
	private final MemberRepository memberRepository;

	public LoginResponse oAuthLogin(LoginRequest request) {
		Member member = loginOrSignUp(request);
		UserDetails userDetails = UserDetailsImpl.builder()
			.id(member.getId())
			.password(member.getPassword())
			.authorities(List.of(new SimpleGrantedAuthority(member.getRole().getValue())))
			.build();
		String accessToken = jwtTokenProvider.createAccessToken(userDetails);
		return LoginResponse.of(accessToken, "refresh_token");
	}

	public Member loginOrSignUp(LoginRequest request) {
		OAuthMember oAuthMember = oidcUserService.getOIDCMember(request.socialType(), request.identityToken());
		return memberRepository.findBySocialId(oAuthMember.socialId()).orElseGet(() -> {
			// 회원가입
			Member newMember = Member.builder()
				.name(request.name())
				.nickname(request.nickname())
				.email(oAuthMember.email())
				.socialId(oAuthMember.socialId())
				.socialType(oAuthMember.socialType())
				.build();
			return memberRepository.save(newMember);
		});
	}
}
