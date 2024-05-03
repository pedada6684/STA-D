package com.klpc.stadspring.domain.contents.label.service;

import com.klpc.stadspring.domain.contents.label.entity.ContentLabel;
import com.klpc.stadspring.domain.contents.label.repository.ContentLabelRepository;
import com.klpc.stadspring.domain.contents.label.service.command.request.AddContentLabelRequestCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContentLabelService {
    private final ContentLabelRepository contentLabelRepository;

    /**
     * 콘텐츠 라벨 등록 서비스
     * @param command
     */
    @Transactional(readOnly = false)
    public void addContentLabel(AddContentLabelRequestCommand command) {
        log.info("콘텐츠 라벨 등록 서비스" + "\n" + "AddContentLabelRequestCommand : " + command);

        ContentLabel newContentLabel = ContentLabel.createContentLabel(
                command.getName(),
                command.getCode());
        contentLabelRepository.save(newContentLabel);
    }
}
