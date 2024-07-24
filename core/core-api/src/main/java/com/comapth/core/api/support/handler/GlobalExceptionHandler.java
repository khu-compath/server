package com.comapth.core.api.support.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.comapth.core.api.support.error.CoreApiException;
import com.comapth.core.api.support.error.ErrorType;
import com.comapth.core.api.support.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({Exception.class})
	public ResponseEntity<ApiResponse<?>> handleAllException(Exception e) {
		log.error(">>>>> Internal Server Error : ", e);
		ErrorType errorType = ErrorType.DEFAULT_ERROR;
		return ResponseEntity.internalServerError().body(ApiResponse.error(errorType, e.getMessage()));
	}

	@ExceptionHandler({CoreApiException.class})
	public ResponseEntity<ApiResponse<?>> handleCustomException(CoreApiException e) {
		ErrorType errorType = e.getErrorType();
		log.warn(">>>>> Common Exception : {}", errorType.getMessage());
		return ResponseEntity
			.status(errorType.getStatus())
			.body(ApiResponse.error(errorType));
	}

	@ExceptionHandler({HttpMessageNotReadableException.class})
	public ResponseEntity<ApiResponse<?>> handleAllException(HttpMessageNotReadableException e) {
		log.error(">>>>> HttpRequest Parsing Error : ", e);
		ErrorType errorType = ErrorType.BAD_REQUEST;
		return ResponseEntity.status(errorType.getStatus()).body(ApiResponse.error(errorType, e.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		// 실패한 validation 을 담을 Map
		Map<String, String> failedValidations = new HashMap<>();
		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		// fieldErrors 를 순회하며 failedValidations 에 담는다.
		fieldErrors.forEach(error -> failedValidations.put(error.getField(), error.getDefaultMessage()));
		ApiResponse<?> errorResponse = ApiResponse.error(ErrorType.BAD_REQUEST, failedValidations);
		return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
	}
}
