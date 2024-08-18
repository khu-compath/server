package com.compath.storage.db.core.redis;

import org.springframework.data.repository.CrudRepository;

public interface TemporaryMemberRepository extends CrudRepository<TemporaryMember, String> {
}
