package com.compath.client.oauth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.compath.client.oauth.client.dto.OIDCPublicKeys;

@FeignClient(value = "apple-api", url = "https://appleid.apple.com/auth")
public interface AppleOAuthClient {
	@GetMapping("/oauth2/v2/keys")
	OIDCPublicKeys getPublicKeys();
}
