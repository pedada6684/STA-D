package com.klpc.stadspring.domain.contents.bookmark.service;

import com.klpc.stadspring.domain.contents.bookmark.controller.response.AddBookmarkResponse;
import com.klpc.stadspring.domain.contents.bookmark.controller.response.DeleteBookmarkResponse;
import com.klpc.stadspring.domain.contents.bookmark.entity.BookmarkedContent;
import com.klpc.stadspring.domain.contents.bookmark.repository.BookmarkedContentRepository;
import com.klpc.stadspring.domain.contents.bookmark.service.command.request.AddBookmarkRequestCommand;
import com.klpc.stadspring.domain.contents.bookmark.service.command.request.DeleteBookmarkRequsetCommand;
import com.klpc.stadspring.domain.contents.detail.entity.ContentDetail;
import com.klpc.stadspring.domain.contents.detail.repository.ContentDetailRepository;
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
    private final ContentDetailRepository detailRepository;

    /**
     * userId로 북마크한 영상의 detail Id 조회
     * @param userId
     * @return
     */
    public List<Long> getDetailIdByUserId(Long userId) {
        List<Long> detailIdList = repository.findDetailIdByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return detailIdList;
    }

    /**
     * userId와 detailId로 북마크 유무 조회
     * @param userId
     * @param detailId
     * @return
     */
    public boolean checkBookmark(Long userId, Long detailId) {
        if(repository.findByUserIdAndContentDetailId(userId, detailId).isPresent()) {
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
        ContentDetail detail = detailRepository.findById(command.getDetailId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        BookmarkedContent newBookmarkedContent = BookmarkedContent.createBookmarkedContent(
                detail,
                user);
        repository.save(newBookmarkedContent);

        return AddBookmarkResponse.builder().result("북마크한 컨텐츠가 성공적으로 생성되었습니다.").build();
    }

    /**
     * 북마크 삭제
     * @param command
     * @return
     */
    public DeleteBookmarkResponse deleteBookmark(DeleteBookmarkRequsetCommand command) {
        log.info("DeleteBookmarkRequsetCommand: "+command);

        BookmarkedContent bookmark = repository.findByUserIdAndContentDetailId(command.getUserId(), command.getDetailId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        repository.delete(bookmark);

        return DeleteBookmarkResponse.builder().result("북마크한 컨텐츠가 성공적으로 삭제되었습니다.").build();
    }
}
