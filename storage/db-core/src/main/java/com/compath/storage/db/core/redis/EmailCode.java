package com.compath.storage.db.core.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Getter;

@Getter
@RedisHash(value = "emailCode", timeToLive = 600L)
public class EmailCode {

	@Id
	private final String email;

	private final String code;

	private boolean isVerified;

	public EmailCode(String email, String code) {
		this.email = email;
		this.code = code;
		this.isVerified = false;
	}

	public boolean matchesCode(String code) {
		return this.code.equalsIgnoreCase(code.trim());
	}

	public EmailCode markAsVerified() {
		this.isVerified = true;
		return this;
	}
}
