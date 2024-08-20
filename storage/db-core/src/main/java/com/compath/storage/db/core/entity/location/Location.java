package com.compath.storage.db.core.entity.location;

import com.compath.storage.db.core.entity.BaseEntity;
import com.compath.storage.db.core.entity.join.JoinEvent;
import com.compath.storage.db.core.entity.member.Member;
import com.compath.storage.db.core.entity.place.Place;
import com.compath.storage.db.core.entity.story.Story;
import com.hwi.core.enums.LocationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "location")
public class Location extends BaseEntity {
	@Column(name = "latitude")
	private double latitude = 0.0;

	@Column(name = "longitude")
	private double longitude = 0.0;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "type")
	private LocationType type;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "place_id")
	private Place place;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "join_event_id")
	private JoinEvent joinEvent;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "story_id")
	private Story story;

	public Location(Member member) {
		this.member = member;
		this.type = LocationType.MEMBER;
	}

	public void update(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
