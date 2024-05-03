package com.klpc.stadspring.domain.contents.label.repository;

import com.klpc.stadspring.domain.contents.label.entity.ContentLabel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentLabelRepository extends JpaRepository<ContentLabel, Long> {
}
