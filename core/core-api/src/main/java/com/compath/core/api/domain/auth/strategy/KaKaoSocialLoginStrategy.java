package com.compath.core.api.domain.auth.strategy;

import org.springframework.stereotype.Component;

import com.compath.storage.db.core.entity.Member;

@Component
public class KaKaoSocialLoginStrategy implements SocialLoginStrategy {
	@Override
	public Member loginOrSignUp(String identityToken) {
		return null;
	}
}
