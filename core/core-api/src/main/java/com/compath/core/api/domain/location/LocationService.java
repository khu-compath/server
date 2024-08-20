package com.compath.core.api.domain.location;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compath.core.api.controller.v1.member.dto.request.LocationUpdateRequest;
import com.compath.core.api.controller.v1.member.dto.response.LocationResponse;
import com.compath.core.api.support.error.CoreApiException;
import com.compath.core.api.support.error.ErrorType;
import com.compath.storage.db.core.entity.location.Location;
import com.compath.storage.db.core.entity.location.LocationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class LocationService {
	private final LocationRepository locationRepository;

	public LocationResponse updateLocation(Long memberId, LocationUpdateRequest request) {
		log.info(">>>>>> Updating location");
		final Location location = findMemberLocation(memberId);
		location.update(request.latitude(), request.longitude());
		return LocationResponse.of(location);
	}

	private Location findMemberLocation(Long memberId) {
		return locationRepository.findMemberLocation(memberId)
			.orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND));
	}
}
