package com.klpc.stadspring.domain.contents.watched.entity;

import com.klpc.stadspring.domain.contents.detail.entity.ContentDetail;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private Long userId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @Column(name = "user_id")
//    private User user;

    private boolean status;
    private String stopTime;
    private String watchedDate;
}
