package com.klpc.stadspring.domain.contents.labelRelationship.repository;

import com.klpc.stadspring.domain.contents.labelRelationship.entity.ContentLabelRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContentLabelRelationshipRepository extends JpaRepository<ContentLabelRelationship, Long> {
    @Query("SELECT clr.contentLabel.id FROM ContentLabelRelationship clr WHERE clr.contentDetail.id = :detailId")
    Long findContentLabel_IdByContentDetail_Id(Long detailId);
}
