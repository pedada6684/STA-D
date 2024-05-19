package com.klpc.stadspring.domain.contents.label.service.command.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddContentLabelRequestCommand {
    private String name;
    private String code;
}
