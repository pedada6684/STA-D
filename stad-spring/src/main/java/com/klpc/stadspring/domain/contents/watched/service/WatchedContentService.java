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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WatchedContentService {
    private final WatchedContentRepository watchedContentRepository;
    private final UserRepository userRepository;
    private final ContentDetailRepository detailRepository;

    /**
     * 시청 중인 영상 조회
     * @param userId
     * @return
     */
    public List<Long> getWatchingContentDetailIdByUserId(Long userId) {
        List<Long> detailIdList = watchedContentRepository.findWatchingContentDetailIdByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return detailIdList;
    }

    /**
     * 시청 중인, 완료한 영상 조회
     * @param userId
     * @return
     */
    public List<Long> getWatchingAndWatchedContentDetailIdByUserId(Long userId) {
        List<Long> detailIdList = watchedContentRepository.findWatchingAndWatchedContentDetailIdByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return detailIdList;
    }

    /**
     * 시청 중인 영상 등록
     * @param command
     * @return
     */
    @Transactional(readOnly = false)
    public AddWatchingContentResponse addWatchingContent(AddWatchingContentCommand command) {
        log.info("AddWatchingContentCommand : " + command);

        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        ContentDetail detail = detailRepository.findById(command.getDetailId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        WatchedContent newWatchedContent = WatchedContent.createWatchedContent(
                detail,
                user,
                LocalDate.now(),
                false,
                "00:00:00");
        watchedContentRepository.save(newWatchedContent);

        return AddWatchingContentResponse.builder().result("시청 중인 컨텐츠가 성공적으로 생성되었습니다.").build();
    }

    /**
     * 시청 중인 영상 수정
     * - 시청 시점 변경
     * - 시청 완료로 변경
     * @param command
     * @return
     */
    @Transactional(readOnly = false)
    public ModifyWatchingContentResponse modifyWatchingContent(ModifyWatchingContentCommand command) {
        log.info("ModifyWatchingContentCommand : " + command);

        WatchedContent updatedWatchedContent = watchedContentRepository.findByUserIdAndDetailId(command.getUserId(), command.getDetailId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        updatedWatchedContent.modifyWatchedContent(
                LocalDate.now(),
                command.isStatus(),
                command.getStopTime()
        );

        Long result = watchedContentRepository.updateWatchedContent(updatedWatchedContent);
        if (result == 0) {
            return ModifyWatchingContentResponse.builder().result("**컨텐츠 시청 시간이 저장되지 않았습니다.**").build();
        } else if (command.isStatus()) {
            return ModifyWatchingContentResponse.builder().result("시청 완료 콘텐츠가 성공적으로 생성되었습니다.").build();
        }
        return ModifyWatchingContentResponse.builder().result("컨텐츠 시청 시간이 성공적으로 저장되었습니다.").build();
    }
}
