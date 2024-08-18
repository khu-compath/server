package com.compath.storage.db.core.entity.member;

import com.compath.storage.db.core.entity.BaseEntity;
import com.hwi.core.enums.member.MemberStatus;
import com.hwi.core.enums.member.Role;
import com.hwi.core.enums.member.SocialType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "member")
public class Member extends BaseEntity {
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "nickname", nullable = false)
	private String nickname;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "university", nullable = false)
	private String university;

	@Column(name = "college", nullable = false)
	private String college;

	@Column(name = "department", nullable = false)
	private String department;

	@Column(name = "social_id", nullable = false, unique = true)
	private String socialId;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "social_type", nullable = false)
	private SocialType socialType;

	@Column(name = "profile_image_url")
	private String profileImageUrl;

	@Column(name = "student_card_image_url")
	private String studentCardImageUrl;

	@Column(name = "is_student_card_certificated")
	private boolean isStudentCardCertificated;

	@Column(name = "is_terms_agreed")
	private boolean isTermsAgreed;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", nullable = false)
	private MemberStatus status;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "role", nullable = false)
	private Role role;

	@Builder
	public Member(String name, String nickname, String email, String socialId, SocialType socialType) {
		this.name = name;
		this.nickname = nickname;
		this.socialId = socialId;
		this.socialType = socialType;
		this.email = email;
		this.status = MemberStatus.ACTIVE;
		this.role = Role.USER;
	}
}
