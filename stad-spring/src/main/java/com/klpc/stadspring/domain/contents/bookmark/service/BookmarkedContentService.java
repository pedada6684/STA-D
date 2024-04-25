package com.klpc.stadspring.domain.contents.bookmark.service;

import com.klpc.stadspring.domain.contents.bookmark.repository.BookmarkedContentRepository;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkedContentService {
    private final BookmarkedContentRepository repository;

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
}
