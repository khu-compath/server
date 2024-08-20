package com.compath.core.api.domain.member;

import org.springframework.stereotype.Service;

import com.compath.core.api.controller.v1.member.dto.FriendRequestResponse;
import com.compath.core.api.controller.v1.member.dto.response.MemberGetResponse;
import com.compath.storage.db.core.entity.member.FriendRequest;
import com.compath.storage.db.core.entity.member.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberReader memberReader;
	private final FriendReader friendReader;
	private final FriendManager friendManager;

	public MemberGetResponse getMember(Long memberId) {
		Member member = memberReader.read(memberId);
		return MemberGetResponse.of(member);
	}

	public FriendRequestResponse sendFriendRequest(Long memberId, Long friendId) {
		Member member = memberReader.read(memberId);
		Member friend = memberReader.read(friendId);
		FriendRequest result = friendManager.sendRequest(member, friend);
		return FriendRequestResponse.from(result);
	}

	public FriendRequestResponse updateFriendRequest(Long memberId, Long friendRequestId, boolean isAccepted) {
		FriendRequest friendRequest = friendReader.readRequest(memberId, friendRequestId);
		if (isAccepted)
			friendRequest.accept();
		else
			friendRequest.reject();
		return FriendRequestResponse.from(friendRequest);
	}
}
