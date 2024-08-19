package com.compath.storage.db.core.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import com.hwi.core.enums.member.SocialType;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 소셜 로그인 인증을 받은 멤버만 회원가입이 가능하도록 관리하는 클래스입니다.
 * 해당 객체는 비회원 멤버가 소셜 로그인을 시도할 때 Redis에 "temporaryMember" 키로 저장되며,
 * 15분 동안 유지됩니다. (TTL: 15분)
 * 회원가입을 진행하기 위해서는 멤버의 소셜 ID 토큰(identityToken)이 Redis에 저장되어 있어야 합니다.
 *
 * - identityToken: 소셜 인증 토큰
 * - socialId: 소셜 서비스에서의 회원 ID
 * - socialType: 소셜 서비스의 종류 (APPLE, KAKAO)
 */
@Getter
@AllArgsConstructor
@RedisHash(value = "temporaryMember", timeToLive = 60 * 15L)
public class TemporaryMember {
	@Id
	private String identityToken;

	private String socialId;

	private SocialType socialType;
}
