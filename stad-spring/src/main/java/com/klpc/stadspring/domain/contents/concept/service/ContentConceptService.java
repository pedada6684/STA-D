package com.klpc.stadspring.domain.contents.concept.service;

import com.klpc.stadspring.domain.contents.category.entity.ContentCategory;
import com.klpc.stadspring.domain.contents.category.repository.ContentCategoryRepository;
import com.klpc.stadspring.domain.contents.categoryRelationship.entity.ContentCategoryRelationship;
import com.klpc.stadspring.domain.contents.categoryRelationship.repository.ContentCategoryRelationshipRepository;
import com.klpc.stadspring.domain.contents.concept.controller.response.GetAllConceptListResponse;
import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import com.klpc.stadspring.domain.contents.concept.repository.ContentConceptRepository;
import com.klpc.stadspring.domain.contents.concept.service.command.request.AddConceptRequestCommand;
import com.klpc.stadspring.domain.contents.concept.service.command.response.GetAllConceptResponseCommand;
import com.klpc.stadspring.domain.contents.concept.service.command.response.GetUpdatedContentResponseCommand;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    /**
     * 모든 콘텐츠 콘셉트 조회
     * @return
     */
    public GetAllConceptListResponse getAllContentList() {
        log.info("getAllContentList 서비스");

        List<ContentConcept> allConcepts = repository.findAll();

        List<GetAllConceptResponseCommand> responseList = new ArrayList<>();
        for(ContentConcept concept:allConcepts) {
            GetAllConceptResponseCommand command = GetAllConceptResponseCommand.builder()
                    .id(concept.getId())
                    .isMovie(concept.isMovie())
                    .title(concept.getTitle())
                    .thumbnailUrl(concept.getThumbnailUrl())
                    .releaseYear(concept.getReleaseYear())
                    .audienceAge(concept.getAudienceAge())
                    .creator(concept.getCreator())
                    .cast(concept.getCast())
                    .playtime(concept.getPlaytime())
                    .description(concept.getDescription())
                    .build();

            responseList.add(command);
        }

        return GetAllConceptListResponse.builder().data(responseList).build();
    }

    /**
     * 최신 콘텐츠 목록 조회
     * @return
     */
    public List<GetUpdatedContentResponseCommand> getUpdatedContent() {
        List<GetUpdatedContentResponseCommand> responseList = new ArrayList<>();

        ContentConcept Jurassic = repository.findById(295L)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        ContentConcept avengers = repository.findById(296L)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        ContentConcept tajja = repository.findById(297L)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        ContentConcept wolf = repository.findById(298L)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        ContentConcept ssam = repository.findById(136L)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        responseList.add(GetUpdatedContentResponseCommand.toCommand(wolf));
        responseList.add(GetUpdatedContentResponseCommand.toCommand(tajja));
        responseList.add(GetUpdatedContentResponseCommand.toCommand(avengers));
        responseList.add(GetUpdatedContentResponseCommand.toCommand(Jurassic));
        responseList.add(GetUpdatedContentResponseCommand.toCommand(ssam));

        return responseList;
    }
}
