package com.klpc.stadspring.domain.contents.bookmark.entity;

import com.klpc.stadspring.domain.contents.detail.entity.ContentDetail;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkedContent {
    @Id
    @Column(name = "watched_content_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "content_detail_id")
    private ContentDetail contentDetail;

    @Column(name = "user_id")
    private Long userId;

//    @ManyToOne
//    @Column(name = "user_id")
//    private User user;
}
