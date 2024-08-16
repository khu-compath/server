package com.compath.core.api.controller.v1.auth.dto;

import com.hwi.core.enums.member.SocialType;

/*
	소셜을 이용한 회원가입만 존재하므로 하나로 동시에 처리
 */
public record LoginRequest(
	SocialType socialType,
	String identityToken
) {
}
