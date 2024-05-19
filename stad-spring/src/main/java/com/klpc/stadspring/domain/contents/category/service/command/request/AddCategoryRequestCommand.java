package com.klpc.stadspring.domain.contents.category.service.command.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddCategoryRequestCommand {
    private boolean isMovie;
    private String name;
}
