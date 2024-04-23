package com.klpc.stadspring.domain.option.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddOptionCommand {
    /**
     *  상품 옵션 등록
     */
    Long productId;
    String name;
    String value;

}
