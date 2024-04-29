package com.klpc.stadspring.util;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataConstructor {
    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void dataConstructor() {
        log.info("dataConstruct start");
    }
}
