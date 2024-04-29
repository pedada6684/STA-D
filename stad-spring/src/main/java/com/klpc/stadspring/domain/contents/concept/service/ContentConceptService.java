package com.klpc.stadspring.domain.contents.concept.service;

import com.klpc.stadspring.domain.contents.category.entity.ContentCategory;
import com.klpc.stadspring.domain.contents.category.repository.ContentCategoryRepository;
import com.klpc.stadspring.domain.contents.categoryRelationship.entity.ContentCategoryRelationship;
import com.klpc.stadspring.domain.contents.categoryRelationship.repository.ContentCategoryRelationshipRepository;
import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import com.klpc.stadspring.domain.contents.concept.repository.ContentConceptRepository;
import com.klpc.stadspring.domain.contents.concept.service.command.request.AddConceptRequestCommand;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContentConceptService {
    public final ContentConceptRepository repository;
    public final ContentCategoryRelationshipRepository relationshipRepository;
    public final ContentCategoryRepository categoryRepository;

    /**
     * id로 콘텐츠 개념 조회
     * @param id
     * @return
     */
    public ContentConcept getContentConceptById(Long id) {
        ContentConcept contentConcept = repository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return contentConcept;
    }

    /**
     * keyword로 title 검색
     * @param keyword
     * @return
     */
    public List<ContentConcept> getContentConceptByKeyword(String keyword) {
        List<ContentConcept> contentConceptList = repository.findByKeyword(keyword)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return contentConceptList;
    }

    /**
     * contentConcept 등록
     * @param command
     */
    @Transactional(readOnly = false)
    public void addConcept(AddConceptRequestCommand command) {
        log.info("AddConceptRequestCommand : " + command);

        ContentConcept newContentConcept = ContentConcept.createContentConcept(
                command.getAudienceAge(),
                command.getPlaytime(),
                command.getDescription(),
                command.getCast(),
                command.getCreator(),
                command.isMovie(),
                command.getReleaseYear(),
                command.getThumbnail(),
                command.getTitle());
        repository.save(newContentConcept);

        for (int i = 0; i < command.getGenre().size(); i++) {
            ContentCategory category = categoryRepository.findByIsMovieAndName(command.isMovie(),command.getGenre().get(i))
                    .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

            ContentCategoryRelationship newRelationship = ContentCategoryRelationship.createRelationship(
                    category,
                    newContentConcept);
            relationshipRepository.save(newRelationship);
        }
    }
}
