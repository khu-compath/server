package com.compath.storage.db.core.entity.location;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LocationRepository extends JpaRepository<Location, Long> {
	@Query("SELECT l FROM Location l WHERE l.type = 'MEMBER' and l.member.id =: memberId")
	Optional<Location> findMemberLocation(Long memberId);
}
