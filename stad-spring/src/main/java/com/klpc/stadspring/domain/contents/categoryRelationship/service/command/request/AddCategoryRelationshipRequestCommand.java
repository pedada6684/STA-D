package com.klpc.stadspring.domain.contents.categoryRelationship.service.command.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddCategoryRelationshipRequestCommand {
    private Long contentConceptId;
    private Long contentCategoryId;
}
