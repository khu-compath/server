package com.compath.core.api.domain.member.location;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compath.core.api.controller.v1.member.dto.request.LocationUpdateRequest;
import com.compath.core.api.controller.v1.member.dto.response.LocationResponse;
import com.compath.core.api.support.error.CoreApiException;
import com.compath.core.api.support.error.ErrorType;
import com.compath.storage.db.core.entity.member.MemberLocation;
import com.compath.storage.db.core.entity.member.MemberLocationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberLocationService {
	private final MemberLocationRepository memberLocationRepository;

	public LocationResponse updateLocation(Long memberId, LocationUpdateRequest request) {
		log.info("Updating location");
		final MemberLocation location = findMemberLocation(memberId);
		if (request != null) {
			location.update(request.latitude(), request.longitude());
		}
		return LocationResponse.of(location);
	}

	private MemberLocation findMemberLocation(Long memberId) {
		return memberLocationRepository.findByMemberId(memberId)
			.orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND));
	}
}
