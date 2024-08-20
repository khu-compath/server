package com.compath.core.api.domain.member;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.compath.storage.db.core.entity.member.Friend;
import com.compath.storage.db.core.entity.member.FriendRepository;
import com.compath.storage.db.core.entity.member.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class FriendManager {
	private final FriendRepository friendRepository;

	@Transactional
	public Friend sendRequest(Member member, Member friend) {
		Friend friendRequest = Friend.builder()
			.sender(member)
			.receiver(friend)
			.build();
		return friendRepository.save(friendRequest);
	}
}
