package com.klpc.stadspring.domain.contents.watched.repository;

import com.klpc.stadspring.domain.contents.watched.entity.WatchedContent;
import com.klpc.stadspring.domain.contents.watched.repository.custom.WatchedContentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchedContentRepository extends JpaRepository<WatchedContent, Long>, WatchedContentRepositoryCustom {
}
