package com.compath.client.oauth.apple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.compath.client.oauth.ClientOauthConfig;

@ContextConfiguration(classes = ClientOauthConfig.class)
@SpringBootTest
public class AppleOAuthUserProviderTest {

	@Autowired
	private AppleOAuthUserProvider appleOAuthUserProvider;

	// @Test
	// void 애플로그인_테스트() {
	// 	String idToken = "";
	// 	AppleMember applePlatformMember = appleOAuthUserProvider.getApplePlatformMember(idToken);
	// }
}
