package com.compath.storage.db.core.entity.member;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FriendRepository extends JpaRepository<Friend, Long> {

	@Query("SELECT f FROM Friend f WHERE f.id = :id AND f.sender =:senderId AND f.state = 'PENDING'")
	Optional<Friend> findPendingRequestById(Long id, Long senderId);

	@Query("SELECT f FROM Friend f WHERE f.state = 'ACCEPTED' AND (f.sender =:memberId OR f.receiver =:memberId)")
	List<Friend> findAllFriends(Long memberId);
}
