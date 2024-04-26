package com.klpc.stadspring.domain.contents.bookmark.service;

import com.klpc.stadspring.domain.contents.bookmark.controller.response.AddBookmarkResponse;
import com.klpc.stadspring.domain.contents.bookmark.controller.response.DeleteBookmarkResponse;
import com.klpc.stadspring.domain.contents.bookmark.entity.BookmarkedContent;
import com.klpc.stadspring.domain.contents.bookmark.repository.BookmarkedContentRepository;
import com.klpc.stadspring.domain.contents.bookmark.service.command.AddBookmarkRequestCommand;
import com.klpc.stadspring.domain.contents.bookmark.service.command.DeleteBookmarkRequsetCommand;
import com.klpc.stadspring.domain.contents.detail.entity.ContentDetail;
import com.klpc.stadspring.domain.contents.detail.repository.ContentDetailRepository;
import com.klpc.stadspring.domain.user.entity.User;
import com.klpc.stadspring.domain.user.repository.UserRepository;
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
public class BookmarkedContentService {
    private final BookmarkedContentRepository repository;
    private final UserRepository userRepository;
    private final ContentDetailRepository detailRepository;

    public List<Long> getDetailIdByUserId(Long userId) {
        List<Long> detailIdList = repository.findDetailIdByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return detailIdList;
    }

    public boolean checkBookmark(Long userId, Long contentId) {
        if(repository.findByUserIdAndContentDetail(userId, contentId).isPresent()) {
            return true;
        }
        return false;
    }
    
    public AddBookmarkResponse addBookmark(AddBookmarkRequestCommand command) {
        log.info("AddBookmarkRequestCommand : " + command);

        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        ContentDetail detail = detailRepository.findById(command.getDetailId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        BookmarkedContent newBookmarkedContent = BookmarkedContent.createToBookmarkedContent(
                detail,
                user);
        repository.save(newBookmarkedContent);

        return AddBookmarkResponse.builder().result("북마크한 컨텐츠가 성공적으로 생성되었습니다.").build();
    }

    public DeleteBookmarkResponse deleteBookmark(DeleteBookmarkRequsetCommand command) {
        log.info("DeleteBookmarkRequsetCommand: "+command);

        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        ContentDetail detail = detailRepository.findById(command.getDetailId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        BookmarkedContent bookmark = repository.findByUserIdAndContentDetail(user.getId(), detail.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        repository.delete(bookmark);

        return DeleteBookmarkResponse.builder().result("북마크한 컨텐츠가 성공적으로 삭제되었습니다.").build();
    }
}
