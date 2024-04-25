package com.klpc.stadspring.domain.contents.watched.service;

import com.klpc.stadspring.domain.contents.detail.entity.ContentDetail;
import com.klpc.stadspring.domain.contents.detail.repository.ContentDetailRepository;
import com.klpc.stadspring.domain.contents.watched.controller.response.AddWatchingContentResponse;
import com.klpc.stadspring.domain.contents.watched.controller.response.ModifyWatchingContentResponse;
import com.klpc.stadspring.domain.contents.watched.entity.WatchedContent;
import com.klpc.stadspring.domain.contents.watched.repository.WatchedContentRepository;
import com.klpc.stadspring.domain.contents.watched.service.command.request.AddWatchingContentCommand;
import com.klpc.stadspring.domain.contents.watched.service.command.request.ModifyWatchingContentCommand;
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
public class WatchedContentService {
    private final WatchedContentRepository watchedContentRepository;
    private final UserRepository userRepository;
    private final ContentDetailRepository detailRepository;

    public List<Long> getDetailIdByUserId(Long userId) {
        List<Long> detailIdList = watchedContentRepository.findDetailIdByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return detailIdList;
    }

    public AddWatchingContentResponse addWatchingContent(AddWatchingContentCommand command) {
        log.info("AddWatchingContentCommand : " + command);

        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        ContentDetail detail = detailRepository.findById(command.getDetailId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        WatchedContent newWatchedContent = WatchedContent.createToWatchedContent(
                detail,
                user,
                false,
                "00:00:00");
        watchedContentRepository.save(newWatchedContent);

        return AddWatchingContentResponse.builder().result("시청 중인 컨텐츠가 성공적으로 생성되었습니다.").build();
    }

    public ModifyWatchingContentResponse modifyWatchingContent(ModifyWatchingContentCommand command) {
        log.info("ModifyWatchingContentCommand : " + command);

        WatchedContent updatedWatchedContent = watchedContentRepository.findByUserIdAndDetailId(command.getUserId(), command.getDetailId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        updatedWatchedContent.modifyWatchedContent(
                command.isStatus(),
                command.getStopTime()
        );

        Long result = watchedContentRepository.updateWatchedContent(updatedWatchedContent);
        if (result == 0)
            return ModifyWatchingContentResponse.builder().result("**컨텐츠 시청 시간이 저장되지 않았습니다.**").build();
        return ModifyWatchingContentResponse.builder().result("컨텐츠 시청 시간이 성공적으로 저장되었습니다.").build();
    }

}
