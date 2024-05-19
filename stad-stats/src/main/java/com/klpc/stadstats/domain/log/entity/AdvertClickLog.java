package com.klpc.stadstats.domain.log.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdvertClickLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "advert_click_log_id")
    private Long id;

    @Column(name = "advert_video_id")
    private Long advertVideoId;

    @Column(name = "advert_id")
    private Long advertId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "content_id")
    private Long contentId;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

    public static AdvertClickLog createNewAdvertClickLog(
            Long advertVideoId,
            Long advertId,
            Long userId,
            Long contentId,
            LocalDateTime regDate
    ) {
        AdvertClickLog advertClickLog = new AdvertClickLog();
        advertClickLog.advertVideoId = advertVideoId;
        advertClickLog.advertId = advertId;
        advertClickLog.userId = userId;
        advertClickLog.contentId = contentId;
        advertClickLog.regDate = regDate;
        return advertClickLog;
    }
}
