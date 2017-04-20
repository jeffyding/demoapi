package com.dingfei.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dingfei.api.dao.entity.MatchHistory;
import com.dingfei.api.dao.repository.MatchHistoryRepository;

@Service
public class MatchService {

	@Autowired
	private MatchHistoryRepository matchHistoryRepository;

	public Page<MatchHistory> findByTargetId(String targetId, Pageable pageable) {
		return matchHistoryRepository.findByTargetId(targetId, pageable);
	}
}
