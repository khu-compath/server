package com.compath.storage.db.core.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import com.hwi.core.enums.member.SocialType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@RedisHash(value = "temporaryMember", timeToLive = 60 * 15L)
public class TemporaryMember {
	@Id
	private String identityToken;

	private String socialId;

	private SocialType socialType;
}
