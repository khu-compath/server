package com.compath.core.api.domain.member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.compath.core.api.controller.v1.member.dto.FriendRequestResponse;
import com.compath.core.api.controller.v1.member.dto.response.MemberGetResponse;
import com.compath.storage.db.core.entity.member.Friend;
import com.compath.storage.db.core.entity.member.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final MemberReader memberReader;
	private final FriendReader friendReader;
	private final FriendManager friendManager;

	public MemberGetResponse getMember(Long memberId) {
		Member member = memberReader.readById(memberId);
		return MemberGetResponse.of(member);
	}

	public FriendRequestResponse sendFriendRequest(Long memberId, Long friendId) {
		Member member = memberReader.readById(memberId);
		Member friend = memberReader.readById(friendId);
		Friend result = friendManager.sendRequest(member, friend);
		return FriendRequestResponse.from(result);
	}

	public FriendRequestResponse updateFriendRequest(Long memberId, Long friendRequestId, boolean isAccepted) {
		Friend friend = friendReader.readRequest(memberId, friendRequestId);
		if (isAccepted)
			friend.accept();
		else
			friend.reject();
		return FriendRequestResponse.from(friend);
	}

}
