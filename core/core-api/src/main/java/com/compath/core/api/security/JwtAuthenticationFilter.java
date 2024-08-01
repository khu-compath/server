package com.compath.core.api.security;

import static org.springframework.util.StringUtils.*;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.compath.core.api.support.error.CoreApiException;
import com.compath.core.api.support.error.ErrorType;
import com.compath.core.api.util.HttpResponseUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final String AUTHENTICATION_HEADER = "Authorization";
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		try {
			String accessToken = jwtTokenProvider.resolveToken(request.getHeader(AUTHENTICATION_HEADER));
			if (hasText(accessToken)) {
				log.info(">>>>>> AccessToken : {}", accessToken);
				jwtTokenProvider.validateAccessToken(accessToken);
				Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (ExpiredJwtException e) {
			log.warn(">>>>>> AccessToken is Expired : ", e);
			HttpResponseUtil.writeErrorResponse(response, new CoreApiException(ErrorType.UNAUTHORIZED, e.getMessage()));
			return;
		} catch (Exception e) {
			log.warn(">>>>>> Authentication Failed : ", e);
			HttpResponseUtil.writeErrorResponse(response, new CoreApiException(ErrorType.UNAUTHORIZED, e.getMessage()));
			return;
		}
		filterChain.doFilter(request, response);
	}
}
