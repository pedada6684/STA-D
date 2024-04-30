package com.klpc.stadalert.domain.connect.service;

import com.klpc.stadalert.domain.connect.service.command.QrLoginCommand;
import com.klpc.stadalert.domain.user.entity.User;
import com.klpc.stadalert.domain.user.service.UserService;
import com.klpc.stadalert.global.response.ErrorCode;
import com.klpc.stadalert.global.response.exception.CustomException;
import com.klpc.stadalert.global.service.SseEmitters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConnectService {

    final SseEmitters sseEmitters;
    final UserService userService;
    public void qrLogin(QrLoginCommand command) {
        User user = userService.findUser(command.getUserId());
        SseEmitter emitter = sseEmitters.emit(
                command.getTvId() + "tv",
                user,
                "Qr login"
        );
        if (emitter == null){
            throw new CustomException(ErrorCode.EMIT_NOT_FOUND);
        }
    }
}
