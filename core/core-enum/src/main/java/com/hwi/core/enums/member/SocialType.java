package com.hwi.core.enums.member;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SocialType {
	KAKAO("kakao"),
	APPLE("apple");

	private final String value;

	SocialType(String value) {
		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return value;
	}

	@JsonCreator
	public static SocialType forValue(String input) {
		try {
			return SocialType.valueOf(input.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("존재하지 않는 소셜 로그인입니다. : " + input);
		}
	}
}
