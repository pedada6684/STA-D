package com.klpc.stadspring.domain.contents.watched.service;

import com.klpc.stadspring.domain.contents.watched.repository.WatchedContentRepository;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WatchedContentService {
    private final WatchedContentRepository repository;

    public List<Long> getDetailIdByUserId(Long userId) {
        List<Long> detailIdList = repository.findDetailIdByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return detailIdList;
    }
}
