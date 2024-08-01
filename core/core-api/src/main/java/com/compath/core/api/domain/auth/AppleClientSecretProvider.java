package com.compath.core.api.domain.auth;

import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AppleClientSecretProvider {

	@Value("${apple.auth.service-id}")
	private String serviceId;

	@Value("${apple.auth.key-id}")
	private String keyId;

	@Value("${apple.auth.team-id}")
	private String teamId;

	@Value("${apple.auth.private-key}")
	private String privateKey;

	public String createClientSecret() {
		SignatureAlgorithm alg = Jwts.SIG.ES256;
		return Jwts.builder()
			.header()
			.add("alg", "ES256")
			.keyId(keyId)
			.and()
			.issuer(teamId)
			.issuedAt(Date.from(Instant.now()))
			.expiration(Date.from(Instant.now().plusSeconds(3600)))
			.audience().add(" https://appleid.apple.com").and()
			.subject(serviceId)
			.signWith(extractPrivateKey())
			.compact();
	}

	private SecretKey extractPrivateKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(privateKey));
	}
}
