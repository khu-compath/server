package com.compath.core.api.util;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.compath.core.api.support.error.CoreApiException;
import com.compath.core.api.support.error.ErrorType;
import com.compath.core.api.support.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class HttpResponseUtil {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static void writeSuccessResponse(HttpServletResponse response, HttpStatus httpStatus, Object body)
		throws IOException {
		String responseBody = objectMapper.writeValueAsString(ApiResponse.success(body));
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(httpStatus.value());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(responseBody);
	}

	public static void writeErrorResponse(HttpServletResponse response, CoreApiException e) throws
		IOException {
		final ErrorType errorType = e.getErrorType();
		final Object errorData = e.getData();
		String responseBody = objectMapper.writeValueAsString(ApiResponse.error(errorType, errorData));
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(errorType.getStatus().value());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(responseBody);
	}
}
