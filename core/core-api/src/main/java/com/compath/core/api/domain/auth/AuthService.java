package com.compath.core.api.domain.auth;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compath.core.api.controller.v1.auth.dto.LoginRequest;
import com.compath.core.api.controller.v1.auth.dto.LoginResponse;
import com.compath.core.api.controller.v1.auth.dto.SignUpRequest;
import com.compath.core.api.domain.member.MemberManager;
import com.compath.core.api.domain.member.MemberReader;
import com.compath.core.api.oauth.OAuthUser;
import com.compath.core.api.oauth.OIDCProvider;
import com.compath.core.api.security.JwtTokenProvider;
import com.compath.core.api.security.TokenPair;
import com.compath.core.api.security.UserDetailsImpl;
import com.compath.storage.db.core.entity.member.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthService {
	private final OIDCProvider oidcProvider;
	private final JwtTokenProvider jwtTokenProvider;
	private final MemberManager memberManager;
	private final MemberReader memberReader;

	// TODO: 전체적으로 개선필요, 토큰헤더로 옮기기
	public LoginResponse loginWithOAuth(LoginRequest request) {
		final OAuthUser oAuthUser = oidcProvider.getOAuthUser(request.socialType(), request.identityToken());
		final Member member = memberReader.readBySocial(oAuthUser.socialId(), oAuthUser.socialType());
		final TokenPair tokenPair = createTokenPair(member);
		return LoginResponse.of(tokenPair.accessToken(), tokenPair.refreshToken());
	}

	public LoginResponse signUpWithOAuth(SignUpRequest request) {
		final OAuthUser oAuthUser = oidcProvider.getOAuthUser(request.socialType(), request.identityToken());
		Member member = memberManager.signUp(request, oAuthUser.socialId(), oAuthUser.socialType());
		TokenPair tokenPair = createTokenPair(member);
		return LoginResponse.of(tokenPair.accessToken(), tokenPair.refreshToken());
	}

	private TokenPair createTokenPair(Member member) {
		return jwtTokenProvider.createToken(
			UserDetailsImpl.builder()
				.id(member.getId())
				.authorities(List.of(new SimpleGrantedAuthority(member.getRole().getValue())))
				.build());
	}
}
