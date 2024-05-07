package com.klpc.stadspring.domain.contents.label.repository;

import com.klpc.stadspring.domain.contents.label.entity.ContentLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContentLabelRepository extends JpaRepository<ContentLabel, Long> {
    @Query("SELECT cl.name FROM ContentLabel cl WHERE cl.id = :id")
    String findNameById(Long id);
}
