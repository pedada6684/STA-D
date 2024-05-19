package com.klpc.stadspring.domain.advertVideo.service.command.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetTotalLogResponse {
    Long totalAdvertVideo;
    Long totalAdvertClick;
    Long totalOrder;
    Long totalOrderCancel;
    Long totalRevenue;
}
