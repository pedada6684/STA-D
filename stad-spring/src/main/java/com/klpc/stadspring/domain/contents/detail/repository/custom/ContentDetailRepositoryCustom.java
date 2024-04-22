package com.klpc.stadspring.domain.contents.detail.repository.custom;

import com.klpc.stadspring.domain.contents.detail.entity.ContentDetail;

import java.util.Optional;

public interface ContentDetailRepositoryCustom {
    Optional<ContentDetail> findById(Long id);
    Optional<String> findVideoUrlById(Long id);
}
