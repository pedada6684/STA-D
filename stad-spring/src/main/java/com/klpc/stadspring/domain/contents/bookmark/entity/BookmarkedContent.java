package com.klpc.stadspring.domain.contents.bookmark.entity;

import com.klpc.stadspring.domain.contents.detail.entity.ContentDetail;
import com.klpc.stadspring.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkedContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_detail_id")
    private ContentDetail contentDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "user_id")
    private User user;

    public static BookmarkedContent createToBookmarkedContent (
            ContentDetail contentDetail,
            User user
            ) {
        BookmarkedContent bookmarkedContent = new BookmarkedContent();
        bookmarkedContent.contentDetail = contentDetail;
        bookmarkedContent.user = user;

        return bookmarkedContent;
    }
}
