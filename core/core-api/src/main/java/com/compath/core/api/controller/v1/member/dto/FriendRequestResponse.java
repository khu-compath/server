package com.compath.core.api.controller.v1.member.dto;

import com.compath.storage.db.core.entity.member.Friend;
import com.hwi.core.enums.FriendState;

public record FriendRequestResponse(
	Long requestId,
	Long senderId,
	Long receiverId,
	FriendState friendState
) {
	public static FriendRequestResponse from(Friend friend) {
		return new FriendRequestResponse(
			friend.getId(),
			friend.getSender().getId(),
			friend.getReceiver().getId(),
			friend.getState()
		);
	}
}
