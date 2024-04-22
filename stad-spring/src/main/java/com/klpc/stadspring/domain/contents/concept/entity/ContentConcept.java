package com.klpc.stadspring.domain.contents.concept.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private String thumbnailUrl;
    private String audienceAge;
    private String creator;
    private String cast;
}
