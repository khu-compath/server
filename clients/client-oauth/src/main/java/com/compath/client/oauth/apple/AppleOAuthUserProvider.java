package com.compath.client.oauth.apple;

import java.security.PublicKey;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.compath.client.oauth.OAuthMember;
import com.compath.client.oauth.apple.client.AppleOAuthClient;
import com.compath.client.oauth.apple.dto.ApplePublicKeys;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AppleOAuthUserProvider {
	private final AppleJwtParser appleJwtParser;
	private final AppleOAuthClient appleClient;
	private final PublicKeyGenerator publicKeyGenerator;

	public OAuthMember getAppleMember(String identityToken) {
		Map<String, String> headers = appleJwtParser.parseHeaders(identityToken);

		ApplePublicKeys applePublicKeys = appleClient.getApplePublicKeys();
		PublicKey publicKey = publicKeyGenerator.generatePublicKey(headers, applePublicKeys);

		Claims claims = appleJwtParser.parsePublicKeyAndGetClaims(identityToken, publicKey);
		return OAuthMember.of(claims.getSubject(), claims.get("email", String.class));
	}

}
