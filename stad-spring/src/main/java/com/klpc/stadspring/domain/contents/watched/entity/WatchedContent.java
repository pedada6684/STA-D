package com.klpc.stadspring.domain.contents.watched.entity;

import com.klpc.stadspring.domain.contents.detail.entity.ContentDetail;
import com.klpc.stadspring.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;


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
    private LocalDateTime dateTime;

    private boolean status;
    @Column(nullable = false)
    private Long stopTime;

    public static WatchedContent createWatchedContent (
            ContentDetail contentDetail,
            User user,
            LocalDateTime dateTime,
            boolean status,
            Long stopTime
    ) {
        WatchedContent watchedContent = new WatchedContent();
        watchedContent.contentDetail = contentDetail;
        watchedContent.user = user;
        watchedContent.dateTime = dateTime;
        watchedContent.status = status;
        watchedContent.stopTime = stopTime;

        return watchedContent;
    }

    public void modifyWatchedContent (
            LocalDateTime dateTime,
            boolean status,
            Long stopTime
    ) {
        this.dateTime = dateTime;
        this.status = status;
        this.stopTime = stopTime;
    }
}
