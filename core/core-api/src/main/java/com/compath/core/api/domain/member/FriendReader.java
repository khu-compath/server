package com.compath.core.api.domain.member;

import org.springframework.stereotype.Component;

import com.compath.core.api.support.error.CoreApiException;
import com.compath.core.api.support.error.ErrorType;
import com.compath.storage.db.core.entity.member.Friend;
import com.compath.storage.db.core.entity.member.FriendRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class FriendReader {
	private final FriendRepository friendRepository;

	public Friend readRequest(Long memberId, Long friendRequestId) {
		return friendRepository.findPendingRequestById(friendRequestId, memberId)
			.orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND));
	}
}
