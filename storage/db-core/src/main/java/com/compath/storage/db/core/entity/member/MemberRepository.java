package com.compath.storage.db.core.entity.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findBySocialId(String socialId);

	Optional<Member> findByEmail(String email);
}
