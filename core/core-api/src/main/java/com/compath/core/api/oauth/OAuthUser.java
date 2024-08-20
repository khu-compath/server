package com.compath.core.api.oauth;

import com.hwi.core.enums.SocialType;

public record OAuthUser(
	SocialType socialType,
	String socialId
) {
}
