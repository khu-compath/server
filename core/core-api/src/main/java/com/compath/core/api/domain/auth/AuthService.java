package com.compath.core.api.domain.auth;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compath.core.api.controller.v1.auth.dto.request.LoginRequest;
import com.compath.core.api.controller.v1.auth.dto.request.SignUpRequest;
import com.compath.core.api.controller.v1.auth.dto.response.LoginResponse;
import com.compath.core.api.domain.member.MemberManager;
import com.compath.core.api.domain.member.MemberReader;
import com.compath.core.api.oauth.OAuthUser;
import com.compath.core.api.oauth.OIDCProvider;
import com.compath.core.api.security.JwtTokenProvider;
import com.compath.core.api.security.TokenPair;
import com.compath.core.api.security.UserDetailsImpl;
import com.compath.core.api.smtp.MailService;
import com.compath.core.api.support.error.CoreApiException;
import com.compath.core.api.support.error.ErrorType;
import com.compath.core.api.util.RandomCodeGenerator;
import com.compath.storage.db.core.entity.member.Member;
import com.compath.storage.db.core.redis.EmailCode;
import com.compath.storage.db.core.redis.EmailCodeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthService {
	private static final int VERIFICATION_CODE_LENGTH = 6;

	private final MemberReader memberReader;
	private final MemberManager memberManager;
	private final OIDCProvider oidcProvider;
	private final JwtTokenProvider jwtTokenProvider;
	private final MailService mailService;
	private final EmailCodeRepository emailCodeRepository;

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

	public void sendEmailCode(String email) {
		final String code = RandomCodeGenerator.generateAlphanumericCode(VERIFICATION_CODE_LENGTH);
		mailService.sendVerificationCode(code, email);
		emailCodeRepository.save(new EmailCode(email, code));
	}

	public void verifyEmailCode(String email, String code) {
		final EmailCode emailCode = emailCodeRepository.findById(email)
			.orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND));
		if (emailCode.matchesCode(code)) {
			emailCodeRepository.save(emailCode.markAsVerified());
		} else {
			throw new CoreApiException(ErrorType.BAD_REQUEST);
		}
	}
}
