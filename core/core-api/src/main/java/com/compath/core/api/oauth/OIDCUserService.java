package com.compath.core.api.oauth;

import java.security.PublicKey;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.compath.client.oauth.client.AppleOAuthClient;
import com.compath.client.oauth.client.KakaoOAuthClient;
import com.compath.client.oauth.client.dto.OIDCPublicKeys;
import com.hwi.core.enums.member.SocialType;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class OIDCUserService {
	private final AppleOAuthClient appleClient;
	private final KakaoOAuthClient kakaoClient;
	private final OIDCJwtParser OIDCJwtParser;
	private final OIDCPublicKeyProvider OIDCPublicKeyProvider;

	public OAuthMember getOIDCMember(SocialType socialType, String identityToken) {
		Map<String, String> headers = OIDCJwtParser.parseHeaders(identityToken);

		OIDCPublicKeys oidcPublicKeys = null;
		if (socialType.equals(SocialType.KAKAO)) {
			oidcPublicKeys = kakaoClient.getPublicKeys();
		} else {
			oidcPublicKeys = appleClient.getPublicKeys();
		}

		// 알맞은 퍼블릭키 찾아와서 identityToken 파싱
		PublicKey publicKey = OIDCPublicKeyProvider.getPublicKeyFromHeaders(headers, oidcPublicKeys);
		Claims claims = OIDCJwtParser.parsePublicKeyAndGetClaims(identityToken, publicKey);
		return new OAuthMember(socialType, claims.getSubject(), claims.get("email", String.class));
	}

}
