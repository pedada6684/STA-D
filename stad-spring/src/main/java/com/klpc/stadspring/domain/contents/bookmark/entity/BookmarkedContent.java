package com.klpc.stadspring.domain.contents.bookmark.entity;

import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
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
    @JoinColumn(name = "content_concept_id")
    private ContentConcept contentConcept;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static BookmarkedContent createBookmarkedContent (
            ContentConcept contentConcept,
            User user
            ) {
        BookmarkedContent bookmarkedContent = new BookmarkedContent();
        bookmarkedContent.contentConcept = contentConcept;
        bookmarkedContent.user = user;

        return bookmarkedContent;
    }
}
