package com.compath.core.api.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.compath.core.api.support.error.CoreApiException;
import com.compath.core.api.support.error.ErrorType;
import com.compath.core.api.util.HttpResponseUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException {
		HttpResponseUtil.writeErrorResponse(
			response,
			new CoreApiException(ErrorType.UNAUTHORIZED, "[Error] 로그인이 필요합니다."));
	}
}
