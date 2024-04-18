package com.klpc.stadspring.domain.contents.detail.entity;

import com.klpc.stadspring.domain.contents.bookmark.entity.BookmarkedContent;
import com.klpc.stadspring.domain.contents.category.entity.ContentCategoryRelationship;
import com.klpc.stadspring.domain.contents.watched.entity.WatchedContent;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentDetail {
    @Id
    @Column(name = "content_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content_concept_id")
    private Long contentConceptId;

    @Column(length = 5)
    private int episode;

    @Column(length = 10)
    private String playtime;

    @Column(name = "video_url")
    private String videoUrl;

    @OneToMany(mappedBy = "contentDetail")
    private List<ContentCategoryRelationship> contentCategoryRelationshipList = new ArrayList<>();

    @OneToMany(mappedBy = "contentDetail")
    private List<BookmarkedContent> bookmarkedContentList = new ArrayList<>();

    @OneToMany(mappedBy = "contentDetail")
    private List<WatchedContent> watchedContentList = new ArrayList<>();
}
