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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

	@Builder.Default
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", nullable = false)
	private MemberStatus status = MemberStatus.ACTIVE;

	@Builder.Default
	@Enumerated(value = EnumType.STRING)
	@Column(name = "role", nullable = false)
	private Role role = Role.USER;
}
