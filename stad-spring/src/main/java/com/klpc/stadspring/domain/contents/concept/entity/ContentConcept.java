package com.klpc.stadspring.domain.contents.concept.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentConcept {
    @Id
    @Column(name = "content_concept_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_movie")
    private boolean isMovie;

    private String title;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "audience_age")
    private String audienceAge;

    private String Creator;

    private String Cast;
}
