package com.compath.client.oauth.apple.dto;

import java.util.List;

public record ApplePublicKeys(List<ApplePublicKey> keys) {

	public ApplePublicKey getMatchingKey(String alg, String kid) {
		return this.keys
			.stream()
			.filter(k -> k.alg().equals(alg) && k.kid().equals(kid))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("Apple JWT 값의 alg, kid 정보가 올바르지 않습니다."));
	}
}
