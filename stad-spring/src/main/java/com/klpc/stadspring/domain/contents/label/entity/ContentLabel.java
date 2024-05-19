package com.klpc.stadspring.domain.contents.label.entity;

import com.klpc.stadspring.domain.contents.labelRelationship.entity.ContentLabelRelationship;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentLabel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;

    @OneToMany(mappedBy = "contentLabel")
    private List<ContentLabelRelationship> contentLabelRelationshipList = new ArrayList<>();

    public static ContentLabel createContentLabel(
            String name,
            String code
    ) {
        ContentLabel contentLabel = new ContentLabel();
        contentLabel.name = name;
        contentLabel.code = code;

        return contentLabel;
    }
}
