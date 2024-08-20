package com.compath.storage.db.core.entity.chat;

import com.compath.storage.db.core.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "chat_room")
public class ChatRoom extends BaseEntity {
	@Column(name = "name")
	private String name;
}
