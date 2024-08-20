package com.compath.core.api.controller.v1.member;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.compath.core.api.annotation.CurrentMember;
import com.compath.core.api.controller.v1.member.dto.request.LocationUpdateRequest;
import com.compath.core.api.controller.v1.member.dto.response.LocationResponse;
import com.compath.core.api.domain.location.LocationService;
import com.compath.core.api.support.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "위치정보 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class MemberLocationController {
	private final LocationService locationService;

	@Operation(summary = "본인 위치 정보 업데이트", description = "본인의 위치정보를 업데이트합니다. 위도와 경도 좌표가 필요합니다.")
	@PostMapping("/members/me/location")
	public ApiResponse<LocationResponse> updateLocation(
		@CurrentMember Long memberId,
		@RequestBody LocationUpdateRequest request) {
		LocationResponse response = locationService.updateLocation(memberId, request);
		return ApiResponse.success(response);
	}
}
