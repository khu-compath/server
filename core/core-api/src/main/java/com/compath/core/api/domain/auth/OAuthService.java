package com.compath.core.api.domain.auth;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compath.core.api.controller.v1.auth.dto.LoginRequest;
import com.compath.core.api.controller.v1.auth.dto.LoginResponse;
import com.compath.core.api.domain.auth.strategy.AppleSocialLoginStrategy;
import com.compath.core.api.domain.auth.strategy.KaKaoSocialLoginStrategy;
import com.compath.core.api.domain.auth.strategy.SocialLoginStrategy;
import com.compath.core.api.security.JwtTokenProvider;
import com.compath.core.api.security.UserDetailsImpl;
import com.compath.storage.db.core.entity.Member;
import com.hwi.core.enums.member.SocialType;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class OAuthService {
	private final AppleSocialLoginStrategy appleSocialLoginStrategy;
	private final KaKaoSocialLoginStrategy kaKaoSocialLoginStrategy;
	private final JwtTokenProvider jwtTokenProvider;

	private Map<SocialType, SocialLoginStrategy> socialLoginStrategyMap;

	@PostConstruct
	public void init() {
		socialLoginStrategyMap = new EnumMap<>(SocialType.class);
		socialLoginStrategyMap.put(SocialType.APPLE, appleSocialLoginStrategy);
		socialLoginStrategyMap.put(SocialType.KAKAO, kaKaoSocialLoginStrategy);
	}

	public LoginResponse login(LoginRequest request) {
		SocialLoginStrategy strategy = socialLoginStrategyMap.get(request.socialType());
		Member member = strategy.loginOrSignUp(request.accessToken());
		UserDetails userDetails = UserDetailsImpl.builder()
			.id(member.getId())
			.password(member.getPassword())
			.authorities(List.of(new SimpleGrantedAuthority(member.getRole().getValue())))
			.build();
		String accessToken = jwtTokenProvider.createAccessToken(userDetails);
		return LoginResponse.of(accessToken, null);
	}

}
