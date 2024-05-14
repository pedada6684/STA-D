package com.klpc.stadspring.domain.contents.watched.entity;

import com.klpc.stadspring.domain.contents.detail.entity.ContentDetail;
import com.klpc.stadspring.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WatchedContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_detail_id")
    private ContentDetail contentDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate date;

    private boolean status;
    private Long stopTime;

    public static WatchedContent createWatchedContent (
            ContentDetail contentDetail,
            User user,
            LocalDate date,
            boolean status,
            Long stopTime
    ) {
        WatchedContent watchedContent = new WatchedContent();
        watchedContent.contentDetail = contentDetail;
        watchedContent.user = user;
        watchedContent.date = date;
        watchedContent.status = status;
        watchedContent.stopTime = stopTime;

        return watchedContent;
    }

    public void modifyWatchedContent (
            LocalDate date,
            boolean status,
            Long stopTime
    ) {
        this.date = date;
        this.status = status;
        this.stopTime = stopTime;
    }
}
