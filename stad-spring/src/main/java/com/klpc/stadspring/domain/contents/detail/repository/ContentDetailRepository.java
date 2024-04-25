package com.klpc.stadspring.domain.contents.detail.repository;

import com.klpc.stadspring.domain.contents.detail.entity.ContentDetail;
import com.klpc.stadspring.domain.contents.detail.repository.custom.ContentDetailRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentDetailRepository extends JpaRepository<ContentDetail, Long>, ContentDetailRepositoryCustom {
}
