package com.klpc.stadspring.domain.contents.labelRelationship.entity;

import com.klpc.stadspring.domain.contents.detail.entity.ContentDetail;
import com.klpc.stadspring.domain.contents.label.entity.ContentLabel;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentLabelRelationship {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_label_id")
    private ContentLabel contentLabel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_detail_id")
    private ContentDetail contentDetail;

    public static ContentLabelRelationship createContentLabelRelationship(
            ContentLabel contentLabel,
            ContentDetail contentDetail
    ) {
        ContentLabelRelationship contentLabelRelationship = new ContentLabelRelationship();
        contentLabelRelationship.contentLabel = contentLabel;
        contentLabelRelationship.contentDetail = contentDetail;

        return contentLabelRelationship;
    }
}
