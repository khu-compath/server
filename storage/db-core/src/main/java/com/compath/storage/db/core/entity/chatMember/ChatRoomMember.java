package com.compath.storage.db.core.entity.chatMember;

import com.compath.storage.db.core.entity.BaseEntity;
import com.compath.storage.db.core.entity.chat.ChatRoom;
import com.compath.storage.db.core.entity.member.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "chat_room_member")
public class ChatRoomMember extends BaseEntity {
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chat_room_id")
	private ChatRoom chatRoom;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

}
