package com.klpc.stadspring.domain.contents.labelRelationship.entity;

import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_label_id")
    private ContentLabel contentLabel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_concept_id")
    private ContentConcept contentConcept;

    public static ContentLabelRelationship createContentLabelRelationship(
            ContentLabel contentLabel,
            ContentConcept contentConcept
    ) {
        ContentLabelRelationship contentLabelRelationship = new ContentLabelRelationship();
        contentLabelRelationship.contentLabel = contentLabel;
        contentLabelRelationship.contentConcept = contentConcept;

        return contentLabelRelationship;
    }
}
