package com.klpc.stadspring.domain.contents.bookmark.repository;

import com.klpc.stadspring.domain.contents.bookmark.entity.BookmarkedContent;
import com.klpc.stadspring.domain.contents.bookmark.repository.custom.BookmarkedContentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookmarkedContentRepository extends JpaRepository<BookmarkedContent, Long>, BookmarkedContentRepositoryCustom {
    Optional<BookmarkedContent> findByUserIdAndContentConceptId(Long userId, Long contentConceptId);

    @Query("""
           SELECT bc FROM BookmarkedContent bc
           WHERE bc.user.id = :userId AND bc.contentConcept.id = :contentConceptId
           """)
    Optional<BookmarkedContent> findByImin(Long userId, Long contentConceptId);
}
