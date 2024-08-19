package com.compath.core.api.controller.v1.member;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.compath.core.api.controller.v1.member.dto.response.MemberGetResponse;
import com.compath.core.api.domain.member.MemberCommandService;
import com.compath.core.api.domain.member.MemberQueryService;
import com.compath.core.api.support.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "회원 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/members")
public class MemberController {
	private final MemberQueryService memberQueryService;
	private final MemberCommandService memberCommandService;

	@Operation(summary = "본인 정보 조회")
	@GetMapping("/me")
	public ApiResponse<MemberGetResponse> getMyInfo(@AuthenticationPrincipal Long memberId) {
		final MemberGetResponse response = memberQueryService.getMember(memberId);
		return ApiResponse.success(response);
	}

}
