package com.compath.storage.db.core.entity.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

	@Query("SELECT f FROM FriendRequest f WHERE f.id = :id AND f.sender =:senderId AND f.state = 'PENDING'")
	Optional<FriendRequest> findPendingRequestById(Long id, Long senderId);
}
