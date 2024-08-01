package com.compath.core.api.domain.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compath.core.api.controller.v1.auth.dto.AuthResponse;
import com.compath.core.api.security.JwtTokenProvider;
import com.compath.core.api.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthService {
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;

	public AuthResponse testLogin(String email) {
		Authentication authenticationToken = new UsernamePasswordAuthenticationToken(email, email);
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		String accessToken = jwtTokenProvider.createAccessToken((UserDetailsImpl)authentication.getPrincipal());
		return AuthResponse.of(accessToken, null);
	}
}
