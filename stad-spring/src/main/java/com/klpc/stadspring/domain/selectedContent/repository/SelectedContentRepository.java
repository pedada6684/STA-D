package com.klpc.stadspring.domain.selectedContent.repository;

import com.klpc.stadspring.domain.selectedContent.entity.SelectedContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SelectedContentRepository extends JpaRepository<SelectedContent,Long> {
    @Query("""
           SELECT sc.advert.id FROM SelectedContent sc
           WHERE sc.fixedContentId = :conceptId
           ORDER BY FUNCTION('RAND')
           LIMIT 1
           """)
    public Long findRandomTopByConceptId(Long conceptId);
}
