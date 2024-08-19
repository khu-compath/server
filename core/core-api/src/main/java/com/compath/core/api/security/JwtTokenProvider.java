package com.compath.core.api.security;

import static org.springframework.util.StringUtils.*;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.compath.storage.db.core.redis.RefreshTokenRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

	private static final String AUTHENTICATION_CLAIM_NAME = "roles";
	private static final String AUTHENTICATION_SCHEME = "Bearer ";

	@Value("${jwt.secret-key}")
	private String secretKey;

	@Value("${jwt.access-expiry-seconds}")
	private int accessExpirySeconds;

	@Value("${jwt.refresh-expiry-seconds}")
	private int refreshExpirySeconds;

	private final RefreshTokenRepository refreshTokenRepository;

	public TokenPair createToken(UserDetails userDetails) {
		String accessToken = createAccessToken(userDetails);
		String refreshToken = createRefreshToken();
		return new TokenPair(accessToken, refreshToken);
	}

	public String createAccessToken(UserDetails userDetails) {
		Instant now = Instant.now();
		Instant expirationTime = now.plusSeconds(accessExpirySeconds);

		String authorities = userDetails.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		return Jwts.builder()
			.subject(userDetails.getUsername())
			.claim(AUTHENTICATION_CLAIM_NAME, authorities)
			.issuedAt(Date.from(now))
			.expiration(Date.from(expirationTime))
			.signWith(extractSecretKey())
			.compact();
	}

	public String createRefreshToken() {
		Instant now = Instant.now();
		Instant expirationTime = now.plusSeconds(refreshExpirySeconds);

		return Jwts.builder()
			.issuedAt(Date.from(now))
			.expiration(Date.from(expirationTime))
			.signWith(extractSecretKey())
			.compact();
	}

	public Authentication getAuthentication(String accessToken) {
		Claims claims = verifyAndExtractClaims(accessToken);

		Collection<? extends GrantedAuthority> authorities = null;
		if (claims.get(AUTHENTICATION_CLAIM_NAME) != null) {
			authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(
				claims.get(AUTHENTICATION_CLAIM_NAME).toString());
		}
		
		// UserDetailsImpl principal = UserDetailsImpl.builder()
		// 	.id(Long.valueOf(claims.getSubject()))
		// 	.password(null)
		// 	.authorities(authorities)
		// 	.build();
		return new UsernamePasswordAuthenticationToken(Long.valueOf(claims.getSubject()), null, authorities);
	}

	public String resolveToken(String bearerToken) {
		if (hasText(bearerToken) && bearerToken.startsWith(AUTHENTICATION_SCHEME)) {
			return bearerToken.substring(AUTHENTICATION_SCHEME.length());
		}
		return null;
	}

	private Claims verifyAndExtractClaims(String accessToken) {
		return Jwts.parser()
			.verifyWith(extractSecretKey())
			.build()
			.parseSignedClaims(accessToken)
			.getPayload();
	}

	public void validateAccessToken(String accessToken) {
		Jwts.parser()
			.verifyWith(extractSecretKey())
			.build()
			.parse(accessToken);
	}

	private SecretKey extractSecretKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}
}
