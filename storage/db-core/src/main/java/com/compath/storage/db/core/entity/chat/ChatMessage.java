package com.compath.storage.db.core.entity.chat;

import com.compath.storage.db.core.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "chat_message")
public class ChatMessage extends BaseEntity {
	@Column(name = "text")
	private String text;

	@Column(name = "sender_id")
	private Long sender_id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chat_room_id")
	private ChatRoom chatRoom;
}
