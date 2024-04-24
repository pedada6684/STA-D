package com.klpc.stadspring.domain.contents.detail.entity;

import com.klpc.stadspring.domain.contents.bookmark.entity.BookmarkedContent;
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
    private String videoUrl;
    private String summary;

    @OneToMany(mappedBy = "contentDetail")
    private List<BookmarkedContent> bookmarkedContentList = new ArrayList<>();

    @OneToMany(mappedBy = "contentDetail")
    private List<WatchedContent> watchedContentList = new ArrayList<>();
}
