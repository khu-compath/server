package com.compath.core.api.oauth;

import com.hwi.core.enums.member.SocialType;

public record OAuthMember(
	SocialType socialType,
	String socialId,
	String email
) {
}
