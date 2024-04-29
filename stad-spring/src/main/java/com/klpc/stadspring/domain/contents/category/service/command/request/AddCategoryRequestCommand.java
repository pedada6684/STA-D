package com.klpc.stadspring.domain.contents.category.service.command.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddCategoryRequestCommand {
    private boolean isMovie;
    private String name;
}
