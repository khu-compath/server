package com.compath.core.api.domain.auth;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compath.core.api.controller.v1.auth.dto.LoginRequest;
import com.compath.core.api.controller.v1.auth.dto.LoginResponse;
import com.compath.core.api.controller.v1.auth.dto.SignUpRequest;
import com.compath.core.api.domain.member.MemberManager;
import com.compath.core.api.oauth.OIDCInfo;
import com.compath.core.api.oauth.OIDCService;
import com.compath.core.api.security.JwtTokenProvider;
import com.compath.core.api.security.TokenPair;
import com.compath.core.api.security.UserDetailsImpl;
import com.compath.core.api.support.error.CoreApiException;
import com.compath.core.api.support.error.ErrorType;
import com.compath.storage.db.core.entity.member.Member;
import com.compath.storage.db.core.entity.member.MemberRepository;
import com.compath.storage.db.core.redis.TemporaryMember;
import com.compath.storage.db.core.redis.TemporaryMemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthService {
	private final OIDCService oidcService;
	private final JwtTokenProvider jwtTokenProvider;
	private final MemberRepository memberRepository;
	private final MemberManager memberManager;
	private final TemporaryMemberRepository temporaryMemberRepository;

	public LoginResponse loginWithOAuth(LoginRequest request) {
		// OAuthResult oAuthResult = oAuthService.loginWithOIDC());
		// Member member = memberManager.findMember();
		// TokenPair tokenPair = createTokenPair(member);

		OIDCInfo OIDCInfo = oidcService.getOIDCInfo(request.socialType(), request.identityToken());

		Member member = memberRepository.findBySocialId(OIDCInfo.socialId()).orElseThrow(() -> {
			temporaryMemberRepository.save(
				new TemporaryMember(request.identityToken(), OIDCInfo.socialId(), OIDCInfo.socialType()));
			return new CoreApiException(ErrorType.BAD_REQUEST, "회원가입이 필요한 유저입니다.");
		});

		TokenPair tokenPair = createTokenPair(member);
		return LoginResponse.success(tokenPair.accessToken(), tokenPair.refreshToken());
	}

	public LoginResponse signUpWithOAuth(SignUpRequest request) {
		TemporaryMember temporaryMember = temporaryMemberRepository.findById(request.identityToken())
			.orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND, "회원가입 시간이 만료되었습니다. 다시 시도해주세요."));

		Member member = memberManager.signUp(request, temporaryMember);

		TokenPair tokenPair = createTokenPair(member);
		return LoginResponse.success(tokenPair.accessToken(), tokenPair.refreshToken());

	}

	private TokenPair createTokenPair(Member member) {
		return jwtTokenProvider.createToken(
			UserDetailsImpl.builder()
				.id(member.getId())
				.authorities(List.of(new SimpleGrantedAuthority(member.getRole().getValue())))
				.build());
	}

}
