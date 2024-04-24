package com.klpc.stadspring.domain.contents.concept.entity;

import com.klpc.stadspring.domain.contents.categoryRelationship.entity.ContentCategoryRelationship;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentConcept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isMovie;
    private String title;
    private String thumbnailUrl;
    private String releaseYear;
    private String audienceAge;
    private String creator;
    private String cast;
    private String playtime;
    @Column(columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "contentConcept")
    private List<ContentCategoryRelationship> contentCategoryRelationshipList = new ArrayList<>();
}
