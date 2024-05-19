package com.klpc.stadspring.domain.contents.categoryRelationship.service;

import com.klpc.stadspring.domain.contents.categoryRelationship.repository.ContentCategoryRelationshipRepository;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContentCategoryRelationshipService {
    private final ContentCategoryRelationshipRepository repository;

    /**
     * categoryId로 카테고리별 conceptId 12개 조회
     * @param categoryId
     * @return
     */
    public List<Long> getConceptIdByCategory(Long categoryId) {
        List<Long> list = repository.findConceptIdByCategory(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return list;
    }
}
