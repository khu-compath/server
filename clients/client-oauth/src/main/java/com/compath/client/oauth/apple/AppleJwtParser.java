package com.compath.client.oauth.apple;

import java.security.PublicKey;
import java.util.Base64;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

@Component
public class AppleJwtParser {
	private static final String IDENTITY_TOKEN_VALUE_DELIMITER = "\\.";
	private static final int HEADER_INDEX = 0;

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public Map<String, String> parseHeaders(String identityToken) {
		try {
			String encodedHeader = identityToken.split(IDENTITY_TOKEN_VALUE_DELIMITER)[HEADER_INDEX];
			String decodedHeader = new String(Base64.getUrlDecoder().decode(encodedHeader));
			return OBJECT_MAPPER.readValue(decodedHeader, Map.class);
		} catch (JsonProcessingException | ArrayIndexOutOfBoundsException e) {
			throw new IllegalArgumentException(); //test
		}
	}

	public Claims parsePublicKeyAndGetClaims(String idToken, PublicKey publicKey) {
		try {
			return Jwts.parser()
				.verifyWith(publicKey)
				.build()
				.parseSignedClaims(idToken)
				.getPayload();
		} catch (ExpiredJwtException e) {
			throw e; //test
		} catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
			throw e; //test
		}
	}
}
