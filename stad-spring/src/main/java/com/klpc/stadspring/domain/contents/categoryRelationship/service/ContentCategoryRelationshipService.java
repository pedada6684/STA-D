package com.klpc.stadspring.domain.contents.categoryRelationship.service;

import com.klpc.stadspring.domain.contents.categoryRelationship.repository.ContentCategoryRelationshipRepository;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ContentCategoryRelationshipService {
    private final ContentCategoryRelationshipRepository repository;

    // 카테고리별 contentId 12개 조회
    public List<Long> getContentIdByCategory(Long CategoryId) {
        List<Long> list = repository.findConceptIdByCategory(CategoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return list;
    }
}
