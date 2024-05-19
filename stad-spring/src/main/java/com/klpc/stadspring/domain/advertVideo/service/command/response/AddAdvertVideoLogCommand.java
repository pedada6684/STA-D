package com.klpc.stadspring.domain.advertVideo.service.command.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddAdvertVideoLogCommand {
    private Long advertVideoId;
    private Long advertId;
    private Long userId;
    private Long contentId;

}
