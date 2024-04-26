package com.klpc.stadspring.domain.contents.category.entity;

import com.klpc.stadspring.domain.contents.categoryRelationship.entity.ContentCategoryRelationship;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isMovie;

    @Column(length = 30)
    private String name;

    @OneToMany(mappedBy = "contentCategory")
    private List<ContentCategoryRelationship> contentCategoryRelationshipList = new ArrayList<>();

    public static ContentCategory createContentCategory (
            boolean isMovie,
            String name
    ) {
        ContentCategory contentCategory = new ContentCategory();
        contentCategory.isMovie = isMovie;
        contentCategory.name = name;

        return contentCategory;
    }
}
