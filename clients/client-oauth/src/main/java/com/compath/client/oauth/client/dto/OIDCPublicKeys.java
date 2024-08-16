package com.compath.client.oauth.client.dto;

import java.util.List;

public record OIDCPublicKeys(List<OIDCPublicKey> keys) {

	public OIDCPublicKey getMatchingKey(String alg, String kid) {
		return this.keys
			.stream()
			.filter(k -> k.alg().equals(alg) && k.kid().equals(kid))
			.findFirst()
			.orElseThrow(
				() -> new IllegalArgumentException("No matching OIDC public key found for algorithm and key ID"));
	}
}
