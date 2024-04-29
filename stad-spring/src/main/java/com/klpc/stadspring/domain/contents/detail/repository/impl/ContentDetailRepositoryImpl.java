package com.klpc.stadspring.domain.contents.detail.repository.impl;

import static com.klpc.stadspring.domain.contents.detail.entity.QContentDetail.contentDetail;

import com.klpc.stadspring.domain.contents.detail.entity.ContentDetail;
import com.klpc.stadspring.domain.contents.detail.repository.custom.ContentDetailRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Optional<ContentDetail> findContentDetailByConceptId(Long conceptId) {
        return Optional.ofNullable(query.select(contentDetail)
                .from(contentDetail)
                .where(contentDetail.contentConceptId.eq(conceptId))
                .limit(1)
                .fetchOne());
    }

    // 인기 영상 추출
    // ========================== 태경 수정 ===================================
    @Override
    public Optional<List<ContentDetail>> findPopularContentDetail() {
        return Optional.of(query.select(contentDetail)
                .from(contentDetail)
                .stream()
                .unordered()
                .limit(3)
                .collect(Collectors.toList()));
    }
}
