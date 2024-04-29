package com.klpc.stadspring.domain.contents.detail.repository.custom;

import com.klpc.stadspring.domain.contents.detail.entity.ContentDetail;

import java.util.List;
import java.util.Optional;

public interface ContentDetailRepositoryCustom {
    Optional<String> findVideoUrlById(Long id);
    Optional<List<ContentDetail>> findContentDetailsByConceptId(Long conceptId);
    Optional<List<ContentDetail>> findPopularContentDetail();
}
