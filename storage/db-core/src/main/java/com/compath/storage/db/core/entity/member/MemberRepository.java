package com.compath.storage.db.core.entity.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hwi.core.enums.SocialType;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmail(String email);

	Optional<Member> findBySocialIdAndSocialType(String socialId, SocialType socialType);
}
