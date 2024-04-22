package com.klpc.stadspring.domain.contents.detail.repository.impl;

import static com.klpc.stadspring.domain.contents.detail.entity.QContentDetail.contentDetail;

import com.klpc.stadspring.domain.contents.detail.entity.ContentDetail;
import com.klpc.stadspring.domain.contents.detail.repository.custom.ContentDetailRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ContentDetailRepositoryImpl implements ContentDetailRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Optional<String> findVideoUrlById(Long id) {
        return Optional.ofNullable(query.select(contentDetail.videoUrl)
                .from(contentDetail)
                .where(contentDetail.id.eq(id))
                .fetchOne());
    }

    @Override
    public Optional<ContentDetail> findById(Long id) {
        return Optional.ofNullable(query.select(contentDetail)
                .from(contentDetail)
                .where(contentDetail.id.eq(id))
                .fetchOne());
    }
}
