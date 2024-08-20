package com.compath.core.api.controller.v1.member.dto;

import com.compath.storage.db.core.entity.member.FriendRequest;
import com.hwi.core.enums.member.FriendRequestState;

public record FriendRequestResponse(
	Long requestId,
	Long senderId,
	Long receiverId,
	FriendRequestState friendRequestState
) {
	public static FriendRequestResponse from(FriendRequest friendRequest) {
		return new FriendRequestResponse(
			friendRequest.getId(),
			friendRequest.getSender().getId(),
			friendRequest.getReceiver().getId(),
			friendRequest.getState()
		);
	}
}
