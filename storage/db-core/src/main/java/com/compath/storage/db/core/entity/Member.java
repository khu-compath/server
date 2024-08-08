package com.compath.storage.db.core.entity;

import com.hwi.core.enums.member.Role;
import com.hwi.core.enums.member.SocialType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "member")
public class Member extends BaseEntity {
	@Column(name = "name")
	private String name;

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "university")
	private String university;

	@Column(name = "college")
	private String college;

	@Column(name = "department")
	private String department;

	@Column(name = "social_id", nullable = false, unique = true)
	private String socialId;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "social_type")
	private SocialType socialType;

	@Column(name = "is_terms_agreed")
	private boolean isTermsAgreed;

	@Column(name = "is_certificated")
	private boolean isCertificated;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "role")
	private Role role;

	public Member(String socialId, String email) {
		this.socialId = socialId;
		this.email = email;
		this.role = Role.GUEST;
	}
}
