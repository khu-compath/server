package com.compath.client.oauth.apple.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.compath.client.oauth.apple.dto.ApplePublicKeys;

@FeignClient(value = "apple-api", url = "https://appleid.apple.com/auth")
public interface AppleOAuthClient {
	@GetMapping("/oauth2/v2/keys")
	ApplePublicKeys getApplePublicKeys();
}
