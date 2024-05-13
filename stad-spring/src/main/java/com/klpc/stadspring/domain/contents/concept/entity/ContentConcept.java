package com.klpc.stadspring.domain.contents.concept.entity;

import com.klpc.stadspring.domain.contents.bookmark.entity.BookmarkedContent;
import com.klpc.stadspring.domain.contents.categoryRelationship.entity.ContentCategoryRelationship;
import com.klpc.stadspring.domain.contents.labelRelationship.entity.ContentLabelRelationship;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentConcept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isMovie;
    private String title;
    @Column(length = 3000)
    private String thumbnailUrl;
    private String releaseYear;
    private String audienceAge;
    private String creator;
    private String cast;
    private String playtime;
    @Column(columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "contentConcept")
    private List<BookmarkedContent> bookmarkedContentList = new ArrayList<>();

    @OneToMany(mappedBy = "contentConcept")
    private List<ContentCategoryRelationship> contentCategoryRelationshipList = new ArrayList<>();

    public static ContentConcept createContentConcept (
            String audienceAge,
            String playtime,
            String description,
            String cast,
            String creator,
            boolean isMovie,
            String releaseYear,
            String thumbnailUrl,
            String title
    ) {
        ContentConcept contentConcept = new ContentConcept();
        contentConcept.isMovie = isMovie;
        contentConcept.title = title;
        contentConcept.thumbnailUrl = thumbnailUrl;
        contentConcept.releaseYear = releaseYear;
        contentConcept.audienceAge = audienceAge;
        contentConcept.creator = creator;
        contentConcept.cast = cast;
        contentConcept.playtime = playtime;
        contentConcept.description = description;

        return contentConcept;
    }
}
