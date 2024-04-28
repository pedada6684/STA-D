package com.klpc.stadspring.domain.contents.concept.service;

import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import com.klpc.stadspring.domain.contents.concept.repository.ContentConceptRepository;
import com.klpc.stadspring.domain.contents.concept.service.command.request.AddConceptRequestCommand;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ContentConceptService {
    public final ContentConceptRepository repository;

    // id로 콘텐츠 개념 조회
    public ContentConcept getContentConceptById(Long id) {
        ContentConcept contentConcept = repository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return contentConcept;
    }

    // keyword로 title 검색
    public List<ContentConcept> getContentConceptByKeyword(String keyword) {
        List<ContentConcept> contentConceptList = repository.findByKeyword(keyword)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return contentConceptList;
    }

    // contentConcept 등록
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
    }
}
