package com.compath.storage.db.core.entity.join;

import com.compath.storage.db.core.entity.BaseEntity;
import com.compath.storage.db.core.entity.member.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "join_participant")
public class JoinParticipant extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "join_event_id")
	private JoinEvent joinEvent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "participant_id")
	private Member participant;
}
