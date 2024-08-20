package com.compath.core.api.domain.member;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.compath.storage.db.core.entity.member.FriendRequest;
import com.compath.storage.db.core.entity.member.FriendRequestRepository;
import com.compath.storage.db.core.entity.member.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class FriendManager {
	private final FriendRequestRepository friendRequestRepository;

	@Transactional
	public FriendRequest sendRequest(Member member, Member friend) {
		FriendRequest friendRequest = FriendRequest.builder()
			.sender(member)
			.receiver(friend)
			.build();
		return friendRequestRepository.save(friendRequest);
	}
}
