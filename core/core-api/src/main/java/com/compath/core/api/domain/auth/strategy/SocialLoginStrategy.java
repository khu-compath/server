package com.compath.core.api.domain.auth.strategy;

import com.compath.storage.db.core.entity.Member;

public interface SocialLoginStrategy {
	Member loginOrSignUp(String identityToken);
}
