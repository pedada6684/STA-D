package com.klpc.stadspring.domain.contents.categoryRelationship.service;

import com.klpc.stadspring.domain.contents.category.entity.ContentCategory;
import com.klpc.stadspring.domain.contents.category.repository.ContentCategoryRepository;
import com.klpc.stadspring.domain.contents.categoryRelationship.entity.ContentCategoryRelationship;
import com.klpc.stadspring.domain.contents.categoryRelationship.repository.ContentCategoryRelationshipRepository;
import com.klpc.stadspring.domain.contents.categoryRelationship.service.command.request.AddCategoryRelationshipRequestCommand;
import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import com.klpc.stadspring.domain.contents.concept.repository.ContentConceptRepository;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ContentCategoryRelationshipService {
    private final ContentCategoryRelationshipRepository repository;
    private final ContentCategoryRepository categoryRepository;
    private final ContentConceptRepository conceptRepository;

    /**
     * 카테고리별 contentId 12개 조회
     */
    public List<Long> getContentIdByCategory(Long CategoryId) {
        List<Long> list = repository.findConceptIdByCategory(CategoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return list;
    }

    /**
     * 카테고리와 콘텐츠 개념 사이 관계 등록
     */
    public void addCategoryRelationship(AddCategoryRelationshipRequestCommand command) {
        ContentCategory category = categoryRepository.findById(command.getContentCategoryId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        ContentConcept concept = conceptRepository.findById(command.getContentConceptId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        ContentCategoryRelationship newRelationship = ContentCategoryRelationship.createRelationship(category, concept);

        repository.save(newRelationship);
    }
}
