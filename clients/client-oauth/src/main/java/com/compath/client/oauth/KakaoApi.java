package com.compath.client.oauth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "kakao-api", url = "${kakao.api.url}")
public interface KakaoApi {

	@RequestMapping(method = RequestMethod.GET, value = "/oauth/authorize", consumes = MediaType.APPLICATION_JSON_VALUE)
	String getToken(String grantType, String clientId, String redirect_uri, String code, String clientSecret);

}
