package com.compath.client.oauth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "apple-api", url = "${apple.api.url}")
public interface AppleApi {
	@PostMapping("/auth/token?grant_type=authorization_code")
	AppleTokenResponse appleAuth(
		@RequestParam("client_id") String clientId,
		@RequestParam("redirect_uri") String redirectUri,
		@RequestParam("code") String code,
		@RequestParam("client_secret") String clientSecret);

}
