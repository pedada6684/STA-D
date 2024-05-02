package com.klpc.stadstats.domain.tmp.service;

import com.klpc.stadstats.domain.tmp.entity.Tmp;
import com.klpc.stadstats.domain.tmp.repository.TmpRepository;
import com.klpc.stadstats.domain.tmp.service.command.TmpCommand;
import com.klpc.stadstats.global.response.ErrorCode;
import com.klpc.stadstats.global.response.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TmpService {

    private final TmpRepository tmpRepository;

    public Tmp tmpMethod(TmpCommand command) {
        log.info("TmpCommand: "+command);
//        Tmp tmp = tmpRepository.findById(command.getId())
//                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        Tmp tmp = Tmp.createChatNotification(command.getType());
        return tmp;
    }
}
