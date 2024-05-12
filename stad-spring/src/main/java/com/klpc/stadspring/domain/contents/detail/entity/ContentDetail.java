package com.klpc.stadspring.domain.contents.detail.entity;

import com.klpc.stadspring.domain.contents.bookmark.entity.BookmarkedContent;
import com.klpc.stadspring.domain.contents.labelRelationship.entity.ContentLabelRelationship;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long contentConceptId;
    private Integer episode;
    @Column(length = 3000)
    private String videoUrl;
    @Column(columnDefinition = "text")
    private String summary;

    @OneToMany(mappedBy = "contentDetail")
    private List<WatchedContent> watchedContentList = new ArrayList<>();

    @OneToMany(mappedBy = "contentDetail")
    private List<ContentLabelRelationship> contentLabelRelationshipList = new ArrayList<>();

    public static ContentDetail createSeriesDetail (
            Long contentConceptId,
            int episode,
            String videoUrl,
            String summary
    ) {
        ContentDetail contentDetail = new ContentDetail();
        contentDetail.contentConceptId = contentConceptId;
        contentDetail.episode = episode;
        contentDetail.videoUrl = videoUrl;
        contentDetail.summary = summary;

        return contentDetail;
    }

    public static ContentDetail createMovieDetail (
            Long contentConceptId,
            String videoUrl
    ) {
        ContentDetail contentDetail = new ContentDetail();
        contentDetail.contentConceptId = contentConceptId;
        contentDetail.videoUrl = videoUrl;

        return contentDetail;
    }
}
