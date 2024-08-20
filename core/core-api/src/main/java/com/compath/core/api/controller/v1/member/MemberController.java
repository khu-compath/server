package com.compath.core.api.controller.v1.member;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.compath.core.api.annotation.CurrentMember;
import com.compath.core.api.controller.v1.member.dto.FriendRequestResponse;
import com.compath.core.api.controller.v1.member.dto.request.FriendAddRequest;
import com.compath.core.api.controller.v1.member.dto.response.MemberGetResponse;
import com.compath.core.api.domain.member.MemberService;
import com.compath.core.api.support.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "회원 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/members")
public class MemberController {
	private final MemberService memberService;

	@Operation(summary = "본인 정보 조회")
	@GetMapping("/me")
	public ApiResponse<MemberGetResponse> getMyInfo(@CurrentMember Long memberId) {
		final MemberGetResponse response = memberService.getMember(memberId);
		return ApiResponse.success(response);
	}

	@Operation(summary = "친구 추가")
	@PostMapping("/friends")
	public ApiResponse<FriendRequestResponse> sendFriendRequest(@CurrentMember Long memberId,
		@RequestBody FriendAddRequest request) {
		FriendRequestResponse response = memberService.sendFriendRequest(memberId, request.friendId());
		return ApiResponse.success(response);
	}

	@Operation(summary = "친구 수락")
	@PatchMapping("/friends/{requestId}/accept")
	public ApiResponse<FriendRequestResponse> acceptFriendRequest(@CurrentMember Long memberId,
		@PathVariable Long requestId) {
		FriendRequestResponse response = memberService.updateFriendRequest(memberId, requestId, true);
		return ApiResponse.success(response);
	}

	@Operation(summary = "친구 거절")
	@PatchMapping("/friends/{requestId}/reject")
	public ApiResponse<FriendRequestResponse> rejectFriendRequest(
		@CurrentMember Long memberId,
		@PathVariable Long requestId) {
		FriendRequestResponse response = memberService.updateFriendRequest(memberId, requestId, false);
		return ApiResponse.success(response);
	}
}
