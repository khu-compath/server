package com.compath.client.oauth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.compath.client.oauth.client.dto.OIDCPublicKeys;

@FeignClient(value = "kakao-api", url = "https://kauth.kakao.com")
public interface KakaoOAuthClient {
	@GetMapping(value = "/.well-known/jwks.json")
	OIDCPublicKeys getPublicKeys();
}
