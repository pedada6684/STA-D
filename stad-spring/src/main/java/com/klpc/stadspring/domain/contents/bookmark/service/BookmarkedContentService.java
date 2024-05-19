package com.klpc.stadspring.domain.contents.bookmark.service;

import com.klpc.stadspring.domain.contents.bookmark.controller.response.AddBookmarkResponse;
import com.klpc.stadspring.domain.contents.bookmark.controller.response.DeleteBookmarkResponse;
import com.klpc.stadspring.domain.contents.bookmark.entity.BookmarkedContent;
import com.klpc.stadspring.domain.contents.bookmark.repository.BookmarkedContentRepository;
import com.klpc.stadspring.domain.contents.bookmark.service.command.request.AddBookmarkRequestCommand;
import com.klpc.stadspring.domain.contents.bookmark.service.command.request.DeleteBookmarkRequsetCommand;
import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import com.klpc.stadspring.domain.contents.concept.repository.ContentConceptRepository;
import com.klpc.stadspring.domain.user.entity.User;
import com.klpc.stadspring.domain.user.repository.UserRepository;
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
public class BookmarkedContentService {
    private final BookmarkedContentRepository repository;
    private final UserRepository userRepository;
    private final ContentConceptRepository conceptRepository;

    /**
     * userId로 북마크한 영상의 detail Id 조회
     * @param userId
     * @return
     */
    public List<Long> getConceptIdByUserId(Long userId) {
        List<Long> conceptIdList = repository.findConceptIdByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return conceptIdList;
    }

    /**
     * userId와 detailId로 북마크 유무 조회
     * @param userId
     * @param conceptId
     * @return
     */
    public boolean checkBookmark(Long userId, Long conceptId) {
        if(repository.findByUserIdAndContentConceptId(userId, conceptId).isPresent()) {
            return true;
        }
        return false;
    }

    /**
     * 북마크 추가
     * @param command
     * @return
     */
    @Transactional(readOnly = false)
    public AddBookmarkResponse addBookmark(AddBookmarkRequestCommand command) {
        log.info("AddBookmarkRequestCommand : " + command);

        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        ContentConcept concept = conceptRepository.findById(command.getConceptId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        BookmarkedContent newBookmarkedContent = BookmarkedContent.createBookmarkedContent(
                concept,
                user);
        repository.save(newBookmarkedContent);

        return AddBookmarkResponse.builder().result("북마크한 컨텐츠가 성공적으로 생성되었습니다.").build();
    }

    /**
     * 북마크 삭제
     * @param command
     * @return
     */
    @Transactional(readOnly = false)
    public DeleteBookmarkResponse deleteBookmark(DeleteBookmarkRequsetCommand command) {
        log.info("DeleteBookmarkRequsetCommand: "+command);
        repository.delete(repository.findByImin(command.getUserId(), command.getConceptId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND)));
        return DeleteBookmarkResponse.builder().result("북마크한 컨텐츠가 성공적으로 삭제되었습니다.").build();
    }
}
