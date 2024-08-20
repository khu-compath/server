package com.compath.core.api.domain.member;

import org.springframework.stereotype.Component;

import com.compath.core.api.support.error.CoreApiException;
import com.compath.core.api.support.error.ErrorType;
import com.compath.storage.db.core.entity.member.FriendRequest;
import com.compath.storage.db.core.entity.member.FriendRequestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class FriendReader {
	private final FriendRequestRepository friendRequestRepository;

	public FriendRequest readRequest(Long memberId, Long friendRequestId) {
		return friendRequestRepository.findPendingRequestById(friendRequestId, memberId)
			.orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND));
	}
}
