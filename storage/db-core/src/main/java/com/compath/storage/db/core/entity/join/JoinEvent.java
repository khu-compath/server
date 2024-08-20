package com.compath.storage.db.core.entity.join;

import com.compath.storage.db.core.entity.BaseEntity;
import com.compath.storage.db.core.entity.member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "join_event")
public class JoinEvent extends BaseEntity {
	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	// 추가 상세정보

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "host_id")
	private Member host;

}
