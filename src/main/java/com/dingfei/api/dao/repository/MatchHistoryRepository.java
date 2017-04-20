package com.dingfei.api.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dingfei.api.dao.entity.MatchHistory;

public interface MatchHistoryRepository extends JpaRepository<MatchHistory, String> {

	Page<MatchHistory> findByTargetId(String targetId, Pageable pageable);

}
