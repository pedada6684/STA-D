package com.klpc.stadspring.domain.advert.controller.response;

import com.klpc.stadspring.domain.advert.service.command.response.GetAdvertAdvertVideo;
import com.klpc.stadspring.domain.advert.service.command.response.GetAdvertResponseCommand;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Getter
@Builder
public class GetAdvertResponse {

    List<GetAdvertResponseCommand> data;

}
