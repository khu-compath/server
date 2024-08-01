package com.compath.core.api.controller.v1.member;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.compath.core.api.controller.v1.member.dto.MemberSignUpRequest;
import com.compath.core.api.domain.member.MemberService;
import com.compath.core.api.support.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/members")
public class MemberController {
	private final MemberService memberService;

	@PostMapping
	public ApiResponse<Long> signUp(@Valid @RequestBody MemberSignUpRequest request) {
		return ApiResponse.success(memberService.signUp(request));
	}
}
