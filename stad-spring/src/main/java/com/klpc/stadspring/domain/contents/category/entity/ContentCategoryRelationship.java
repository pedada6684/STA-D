package com.klpc.stadspring.domain.contents.category.entity;

import com.klpc.stadspring.domain.contents.detail.entity.ContentDetail;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentCategoryRelationship {
    @Id
    @Column(name = "content_category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_category_id")
    private ContentCategory contentCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_detail_id")
    private ContentDetail contentDetail;
}
