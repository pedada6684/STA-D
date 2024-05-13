package com.klpc.stadstream.domain.contents.detail.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long contentConceptId;
    private Integer episode;
    @Column(length = 3000)
    private String videoUrl;
    @Column(columnDefinition = "text")
    private String summary;
}
