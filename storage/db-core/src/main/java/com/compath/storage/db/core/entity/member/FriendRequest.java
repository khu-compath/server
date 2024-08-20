package com.compath.storage.db.core.entity.member;

import com.compath.storage.db.core.entity.BaseEntity;
import com.hwi.core.enums.member.FriendRequestState;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "friend_request")
public class FriendRequest extends BaseEntity {
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id")
	private Member sender;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_id")
	private Member receiver;

	@Builder.Default
	@Enumerated(value = EnumType.STRING)
	@Column(name = "friend_request_state")
	private FriendRequestState state = FriendRequestState.PENDING;

	public void accept() {
		this.state = FriendRequestState.ACCEPTED;
	}

	public void reject() {
		this.state = FriendRequestState.REJECTED;
	}
}
