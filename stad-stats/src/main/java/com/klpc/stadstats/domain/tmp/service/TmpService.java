package com.klpc.stadstats.domain.tmp.service;

import com.klpc.stadstats.domain.tmp.entity.Tmp;
import com.klpc.stadstats.domain.tmp.service.command.TmpCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TmpService {

    public Tmp tmpMethod(TmpCommand command) {
        log.info("TmpCommand: "+command);
        Tmp tmp = Tmp.createChatNotification(command.getType());
        return tmp;
    }
}
