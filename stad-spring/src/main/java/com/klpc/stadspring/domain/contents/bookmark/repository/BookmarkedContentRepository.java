package com.klpc.stadspring.domain.contents.bookmark.repository;

import com.klpc.stadspring.domain.contents.bookmark.entity.BookmarkedContent;
import com.klpc.stadspring.domain.contents.bookmark.repository.custom.BookmarkedContentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkedContentRepository extends JpaRepository<BookmarkedContent, Long>, BookmarkedContentRepositoryCustom {
}
