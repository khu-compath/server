package com.compath.client.oauth.client.dto;

public record OIDCPublicKey(
	String kty,
	String kid,
	String use,
	String alg,
	String n,
	String e
) {
}
