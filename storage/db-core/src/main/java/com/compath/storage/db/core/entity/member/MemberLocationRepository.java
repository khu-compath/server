package com.compath.storage.db.core.entity.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberLocationRepository extends JpaRepository<MemberLocation, Long> {
	Optional<MemberLocation> findByMemberId(Long memberId);
}
