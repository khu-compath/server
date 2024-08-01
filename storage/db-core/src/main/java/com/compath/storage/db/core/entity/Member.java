package com.compath.storage.db.core.entity;

import com.compath.storage.db.core.entity.enums.IdentityProvider;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "university")
	private String university;

	@Column(name = "college")
	private boolean college;

	@Column(name = "department")
	private boolean department;

	@Column(name = "social_id")
	private String socialId;

	@Column(name = "social_name")
	private String socialName;

	@Column(name = "identity_provider")
	private IdentityProvider identityProvider;

	@Column(name = "is_terms_agreed")
	private boolean isTermsAgreed;

	@Column(name = "role")
	private Role role;

	@Column(name = "is_certificated")
	private boolean isCertificated;

	@Builder
	public Member(String name, String email, String password) {
		// for test
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = Role.GUEST;
	}
}
