package com.compath.core.api.oauth;

import com.hwi.core.enums.member.SocialType;

public record OIDCInfo(
	SocialType socialType,
	String socialId
) {
}
