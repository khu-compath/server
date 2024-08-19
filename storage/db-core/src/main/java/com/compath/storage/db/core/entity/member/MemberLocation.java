package com.compath.storage.db.core.entity.member;

import com.compath.storage.db.core.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "member_location")
public class MemberLocation extends BaseEntity {
	@Column(name = "member_id")
	private Long memberId;

	@Column(name = "latitude")
	private double latitude = 0.0;

	@Column(name = "longitude")
	private double longitude = 0.0;

	public MemberLocation(Long memberId) {
		this.memberId = memberId;
	}

	public void update(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
