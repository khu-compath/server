package com.compath.client.oauth;

public record OAuthMember(
	String socialId,
	String email
) {
	public static OAuthMember of(String socialId, String email) {
		return new OAuthMember(socialId, email);
	}
}
